package ru.yandex.practicum;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.product.*;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.params.Pageable;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDtoList getProductList(ProductCategory category, Pageable pageable) {
        Sort sort = Sort.by(pageable.getSortParams().stream()
                .map(s -> switch (s.getDirectionOrDefault()) {
                    case ASC -> Sort.Order.asc(s.getSort());
                    case DESC -> Sort.Order.desc(s.getSort());
                })
                .toList());

        Predicate predicate = QProduct.product.productCategory.eq(category);
        org.springframework.data.domain.Pageable page = PageRequest.of(pageable.getPage(), pageable.getSize(), sort);
        return ProductDtoList.builder()
                .content(ProductMapper.INSTANCE.toCategoryDtoList(productRepository.findAll(predicate, page).toList()))
                .sort(SortMapper.INSTANCE.toSortDtoList(pageable.getSortParams()))
                .build();
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
        product.setProductState(ProductState.DEACTIVATE);
        return productRepository.save(product).getProductState().equals(ProductState.DEACTIVATE);
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
