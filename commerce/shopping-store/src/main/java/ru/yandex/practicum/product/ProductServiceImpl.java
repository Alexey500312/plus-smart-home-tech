package ru.yandex.practicum.product;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductListDto;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.QuantityState;
import ru.yandex.practicum.dto.enums.State;
import ru.yandex.practicum.exception.ProductNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductListDto getProductList(ProductCategory category, Pageable pageable) {
        Predicate predicate = QProduct.product.productCategory.eq(category);
        return ProductMapper.INSTANCE.toProdictListDto(
                ProductMapper.INSTANCE.toProductDtoList(productRepository.findAll(predicate, pageable).toList()),
                SortMapper.INSTANCE.toSortDtoList(pageable.getSort().toList()));
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        return ProductMapper.INSTANCE.toProductDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        getProductById(productDto.getProductId());
        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        return ProductMapper.INSTANCE.toProductDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public Boolean removeProductFromStore(UUID productId) {
        Product product = getProductById(productId);
        product.setProductState(State.DEACTIVATE);
        return productRepository.save(product).getProductState().equals(State.DEACTIVATE);
    }

    @Override
    @Transactional
    public Boolean setProductQuantityState(UUID productId, QuantityState quantityState) {
        Product product = getProductById(productId);
        product.setQuantityState(quantityState);
        return productRepository.save(product).getQuantityState().equals(quantityState);
    }

    @Override
    public ProductDto findProductById(UUID productId) {
        return ProductMapper.INSTANCE.toProductDto(getProductById(productId));
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Не найден товар с id " + productId));
    }
}
