package ru.yandex.practicum;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.product.ProductDto;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);

    List<ProductDto> toCategoryDtoList(List<Product> products);
}
