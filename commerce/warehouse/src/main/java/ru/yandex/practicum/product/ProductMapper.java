package ru.yandex.practicum.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.NewProductInWarehouseRequest;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "newProduct.productId", target = "productId")
    @Mapping(source = "newProduct.dimension.width", target = "width")
    @Mapping(source = "newProduct.dimension.height", target = "height")
    @Mapping(source = "newProduct.dimension.depth", target = "depth")
    @Mapping(source = "newProduct.fragile", target = "fragile")
    @Mapping(source = "newProduct.weight", target = "weight")
    @Mapping(target = "quantity", expression = "java(0)")
    Product toProduct(NewProductInWarehouseRequest newProduct);
}
