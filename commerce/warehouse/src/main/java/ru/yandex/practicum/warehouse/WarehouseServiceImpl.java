package ru.yandex.practicum.warehouse;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.address.AddressMapper;
import ru.yandex.practicum.address.AddressRepository;
import ru.yandex.practicum.booking.OrderBooking;
import ru.yandex.practicum.booking.OrderBookingRepository;
import ru.yandex.practicum.booking.QOrderBooking;
import ru.yandex.practicum.bookingproducts.OrderBookingProduct;
import ru.yandex.practicum.bookingproducts.OrderBookingProductRepository;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoOrderBookingFoundException;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.product.Product;
import ru.yandex.practicum.product.ProductMapper;
import ru.yandex.practicum.product.ProductRepository;
import ru.yandex.practicum.product.QProduct;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseServiceImpl implements WarehouseService {
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderBookingRepository orderBookingRepository;
    private final OrderBookingProductRepository orderBookingProductRepository;

    @Override
    public AddressDto getAddress() {
        return AddressMapper.INSTANSE.toAddressDto(addressRepository.getAddress());
    }

    @Override
    @Transactional
    public void createProduct(NewProductInWarehouseRequest product) {
        if (productRepository.findById(product.getProductId()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException(
                    "Товар с id = " + product.getProductId() + " уже зарегистрирован на складе");
        }
        productRepository.save(ProductMapper.INSTANCE.toProduct(product));
    }

    @Override
    public BookedProductsDto checkShoppingCart(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Product> warehouseProducts = getWarehouseProducts(shoppingCartDto.getProducts().keySet());
        return checkProducts(shoppingCartDto.getProducts(), warehouseProducts);
    }

    @Override
    @Transactional
    public void addProduct(AddProductToWarehouseRequest addProduct) {
        Product product = getProductById(addProduct.getProductId());
        product.setQuantity(product.getQuantity() + addProduct.getQuantity());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        OrderBooking orderBooking = getOrderBooking(shippedToDeliveryRequest.getOrderId());
        orderBooking.setDeliveryId(shippedToDeliveryRequest.getDeliveryId());
        orderBookingRepository.save(orderBooking);
    }

    @Override
    @Transactional
    public void returnProducts(Map<UUID, Integer> products) {
        Map<UUID, Product> warehouseProducts = getWarehouseProducts(products.keySet());
        checkNotFountProducts(products, warehouseProducts);
        productRepository.saveAll(warehouseProducts.values().stream()
                .peek(p -> p.setQuantity(p.getQuantity() + products.get(p.getProductId())))
                .toList());
    }

    @Override
    @Transactional
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) {
        Map<UUID, Product> warehouseProducts = getWarehouseProducts(assemblyProductsForOrderRequest.getProducts().keySet());
        BookedProductsDto bookedProductsDto = checkProducts(assemblyProductsForOrderRequest.getProducts(), warehouseProducts);
        final Long bookingId = orderBookingRepository.save(OrderBooking.builder()
                .orderId(assemblyProductsForOrderRequest.getOrderId())
                .build()).getId();
        orderBookingProductRepository.saveAll(assemblyProductsForOrderRequest.getProducts().entrySet().stream()
                .map(p -> OrderBookingProduct.builder()
                        .orderBookingId(bookingId)
                        .productId(p.getKey())
                        .quantity(p.getValue())
                        .build())
                .toList());
        productRepository.saveAll(warehouseProducts.values().stream()
                .peek(p -> p.setQuantity(p.getQuantity() - assemblyProductsForOrderRequest.getProducts().get(p.getProductId())))
                .toList());
        return bookedProductsDto;
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Не найден товар с id " + productId));
    }

    private OrderBooking getOrderBooking(UUID orderId) {
        Predicate predicate = QOrderBooking.orderBooking.orderId.eq(orderId);
        Iterator<OrderBooking> iterator = orderBookingRepository.findAll(predicate).iterator();
        if (!iterator.hasNext()) {
            throw new NoOrderBookingFoundException("Не найдено бронирование заказа");
        }
        return iterator.next();
    }

    private BookedProductsDto checkProducts(Map<UUID, Integer> productsChecked, Map<UUID, Product> warehouseProducts) {
        final BookedProductsDto booked = BookedProductsDto.builder()
                .deliveryWeight(0.)
                .deliveryVolume(0.)
                .fragile(false)
                .build();
        checkNotFountProducts(productsChecked, warehouseProducts);
        List<UUID> productIdsEnded = productsChecked.entrySet().stream()
                .filter(p -> warehouseProducts.get(p.getKey()).getQuantity().compareTo(p.getValue()) < 0)
                .map(Map.Entry::getKey)
                .toList();
        if (!productIdsEnded.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouseException(
                    "Товар из корзины не находится в требуемом количестве на складе для id " + productIdsEnded);
        }
        warehouseProducts.values().forEach(p -> {
            Integer quantity = productsChecked.get(p.getProductId());
            booked.setDeliveryVolume(
                    booked.getDeliveryVolume() + (p.getHeight() * p.getWeight() * p.getDepth() * quantity));
            booked.setDeliveryWeight(booked.getDeliveryWeight() + (p.getWeight() * quantity));
            booked.setFragile(Boolean.TRUE.equals(booked.getFragile()) || Boolean.TRUE.equals(p.getFragile()));
        });
        return booked;
    }

    private Map<UUID, Product> getWarehouseProducts(Set<UUID> productIds) {
        Predicate predicate = QProduct.product.productId.in(productIds);
        Map<UUID, Product> products = new HashMap<>();
        productRepository.findAll(predicate).forEach(p -> products.put(p.getProductId(), p));
        return products;
    }

    private void checkNotFountProducts(Map<UUID, Integer> productsChecked, Map<UUID, Product> warehouseProducts) {
        List<UUID> productIdsNotFound = productsChecked.keySet().stream()
                .filter(p -> warehouseProducts.get(p) == null)
                .toList();
        if (!productIdsNotFound.isEmpty()) {
            throw new NoSpecifiedProductInWarehouseException("Не найдены товары: " + productIdsNotFound);
        }
    }
}
