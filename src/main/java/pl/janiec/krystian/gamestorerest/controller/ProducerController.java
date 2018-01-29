package pl.janiec.krystian.gamestorerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProducerListDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProductListDTO;
import pl.janiec.krystian.gamestorerest.service.ProducerService;
import pl.janiec.krystian.gamestorerest.service.ProductService;

@RestController
@RequestMapping(ProducerController.PRODUCERS_URL)
public class ProducerController {

    public static final String PRODUCERS_URL = "/api/producers/";

    private final ProducerService producerService;
    private final ProductService productService;

    @Autowired
    public ProducerController(ProducerService producerService, ProductService productService) {
        this.producerService = producerService;
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProducerListDTO getAllProducers() {
        return new ProducerListDTO(producerService.getAllProducers());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProducerDTO getProducerById(@PathVariable Long id) {
        return producerService.getProducerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDTO createNewProducer(@RequestBody ProducerDTO producerDTO) {
        return producerService.createProducer(producerDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProducerDTO updateProducer(@RequestBody ProducerDTO producerDTO, @PathVariable Long id) {
        return producerService.updateProducer(producerDTO, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProducer(@PathVariable Long id) {
        producerService.deleteProducerById(id);
    }

    @GetMapping("{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProductsForProducerWithId(@PathVariable Integer id) {
        return new ProductListDTO(productService.getAllProductsForProducerWithId(id));
    }

    @PostMapping("{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProductForProducerWithId(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.createProductForProducerWithId(id, productDTO);
    }
}
