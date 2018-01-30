package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.ProductMapper;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.domain.Product;
import pl.janiec.krystian.gamestorerest.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.janiec.krystian.gamestorerest.controller.CategoryController.CATEGORIES_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProducerController.PRODUCERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProductController.PRODUCTS_URL;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    setProductDtoUrls(productDTO,product);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id);
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        setProductDtoUrls(productDTO,product);
        return productDTO;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return saveProductAndReturnProductDTO(productMapper.productDTOtoProduct(productDTO));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long id) {
        Product product = productMapper.productDTOtoProduct(productDTO);
        product.setId(id);
        return saveProductAndReturnProductDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<ProductDTO> getAllProductsForProducerWithId(Integer id) {
        return productRepository.findAll()
                .stream()
                .filter(producer -> producer.getProducerId().equals(id))
                .map(product -> {
                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    setProductDtoUrls(productDTO,product);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProductForProducerWithId(Integer id, ProductDTO productDTO) {
        return productRepository.findAll()
                .stream()
                .filter(producer -> producer.getProducerId().equals(id))
                .map(this::saveProductAndReturnProductDTO)
                .findAny().orElseGet(null);
    }

    private ProductDTO saveProductAndReturnProductDTO(Product product) {
        Product productInDB = productRepository.save(product);
        ProductDTO productDTO = productMapper.productToProductDTO(productInDB);
        setProductDtoUrls(productDTO,productInDB);
        return productDTO;
    }

    private void setProductDtoUrls(ProductDTO productDTO, Product product){
        productDTO.setProductUrl(PRODUCTS_URL + product.getId());
        productDTO.setCategoryUrl(CATEGORIES_URL + product.getCategoryName());
        productDTO.setProducerUrl(PRODUCERS_URL + product.getProducerId());
    }
}
