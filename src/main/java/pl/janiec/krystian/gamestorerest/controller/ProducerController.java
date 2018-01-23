package pl.janiec.krystian.gamestorerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProducerListDTO;
import pl.janiec.krystian.gamestorerest.service.ProducerService;

@RestController
@RequestMapping(ProducerController.PRODUCER_URL)
public class ProducerController {

    static final String PRODUCER_URL = "/api/producers/";

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
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
}
