package pl.janiec.krystian.gamestorerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProductListDTO;
import pl.janiec.krystian.gamestorerest.service.ProductService;

@RestController
@RequestMapping(ProductController.PRODUCTS_URL)
public class ProductController {

    public static final String PRODUCTS_URL = "/api/products/";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return new ProductListDTO(productService.getAllProducts());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        return productService.updateProduct(productDTO, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
