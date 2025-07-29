package ru.yandex.practicum.delivery;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.address.Address;
import ru.yandex.practicum.address.AddressMapper;
import ru.yandex.practicum.address.AddressRepository;
import ru.yandex.practicum.address.QAddress;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.enums.DeliveryState;
import ru.yandex.practicum.dto.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.WarehouseClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private static final double BASE_PRICE = 5;
    private static final double ADDRESS1_COEFF = 1;
    private static final double ADDRESS2_COEFF = 2;
    private static final double ADDRESS_NOT_EQUALS_FROM_TO = 0.2;
    private static final double FRAGILE_COEFF = 0.2;
    private static final double WEIGHT_COEFF = 0.3;
    private static final double VOLUME_COEFF = 0.2;

    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Address fromAddress = findOrCreateAddress(deliveryDto.getFromAddress());
        Address toAddress = findOrCreateAddress(deliveryDto.getToAddress());
        Delivery delivery = DeliveryMapper.INSTANCE.toDelivery(deliveryDto);
        delivery.setFromAddress(fromAddress);
        delivery.setToAddress(toAddress);
        delivery.setDeliveryState(DeliveryState.CREATED);
        return DeliveryMapper.INSTANCE.toDeliveryDto(deliveryRepository.save(delivery));
    }

    @Override
    public void delivery(UUID orderId) {
        Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.deliverOrder(delivery.getOrderId());
        deliveryRepository.save(delivery);
    }

    @Override
    public void shippedToDelivery(UUID orderId) {
        Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        orderClient.assemblyOrder(delivery.getOrderId());
        warehouseClient.shippedToDelivery(ShippedToDeliveryRequest.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .build());
        deliveryRepository.save(delivery);
    }

    @Override
    public void deliveryFailed(UUID orderId) {
        Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.deliverOrderFailed(delivery.getOrderId());
        deliveryRepository.save(delivery);
    }

    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        Delivery delivery = findDeliveryById(orderDto.getOrderId());
        delivery.setDeliveryVolume(orderDto.getDeliveryVolume());
        delivery.setDeliveryWeight(orderDto.getDeliveryWeight());
        delivery.setFragile(orderDto.getFragile());
        deliveryRepository.save(delivery);

        double result = BASE_PRICE;
        result += (delivery.getFromAddress().getCountry().equalsIgnoreCase("ADDRESS_1")
                ? result * ADDRESS1_COEFF
                : result * ADDRESS2_COEFF);
        result += (delivery.getFragile() ? result * FRAGILE_COEFF : result);
        result += (delivery.getDeliveryWeight() * WEIGHT_COEFF);
        result += (delivery.getDeliveryVolume() * VOLUME_COEFF);
        if (!equalsAddress(delivery.getFromAddress(), delivery.getToAddress())) {
            result += (result * ADDRESS_NOT_EQUALS_FROM_TO);
        }

        return BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP);
    }

    private Address findOrCreateAddress(AddressDto addressDto) {
        Address address = AddressMapper.INSTANCE.toAddress(addressDto);
        Predicate predicate = QAddress.address.country.equalsIgnoreCase(address.getCountry())
                .and(QAddress.address.city.equalsIgnoreCase(address.getCity()))
                .and(QAddress.address.street.equalsIgnoreCase(address.getStreet()))
                .and(QAddress.address.house.equalsIgnoreCase(address.getHouse()))
                .and(QAddress.address.flat.equalsIgnoreCase(address.getFlat()));
        Iterator<Address> iterator = addressRepository.findAll(predicate).iterator();
        if (iterator.hasNext()) {
            address = iterator.next();
        } else {
            address = addressRepository.save(address);
        }
        return address;
    }

    private Delivery findDeliveryById(UUID orderId) {
        Predicate predicate = QDelivery.delivery.orderId.eq(orderId);
        Iterator<Delivery> iterator = deliveryRepository.findAll(predicate).iterator();
        if (!iterator.hasNext()) {
            throw new NoDeliveryFoundException("Не найдена доставка");
        }
        return iterator.next();
    }

    private boolean equalsAddress(Address fromAddress, Address toAddress) {
        return fromAddress.getCountry().equalsIgnoreCase(toAddress.getCountry())
                && fromAddress.getCity().equalsIgnoreCase(toAddress.getCountry())
                && fromAddress.getStreet().equalsIgnoreCase(toAddress.getStreet())
                && fromAddress.getHouse().equalsIgnoreCase(toAddress.getHouse())
                && fromAddress.getFlat().equalsIgnoreCase(toAddress.getFlat());
    }
}
