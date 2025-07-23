package ru.yandex.practicum.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductListDto;
import ru.yandex.practicum.dto.SortDto;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

    @Mapping(source = "productDtos", target = "content")
    @Mapping(source = "sortDtos", target = "sort")
    ProductListDto toProdictListDto(List<ProductDto> productDtos, List<SortDto> sortDtos);
}
