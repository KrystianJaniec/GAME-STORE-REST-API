package pl.janiec.krystian.gamestorerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.domain.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "productName", target = "name"),
            @Mapping(source = "productDescription", target = "description"),
            @Mapping(source = "productPrice", target = "price")})
    ProductDTO productToProductDTO(Product product);

    @Mappings({
            @Mapping(source = "name", target = "productName"),
            @Mapping(source = "description", target = "productDescription"),
            @Mapping(source = "price", target = "productPrice")})
    Product productDTOtoProduct(ProductDTO productDTO);
}
