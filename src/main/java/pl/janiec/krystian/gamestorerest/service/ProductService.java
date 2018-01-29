package pl.janiec.krystian.gamestorerest.service;

import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO, Long id);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProductsForProducerWithId(Integer id);

    ProductDTO createProductForProducerWithId(Integer id, ProductDTO productDTO);
}
