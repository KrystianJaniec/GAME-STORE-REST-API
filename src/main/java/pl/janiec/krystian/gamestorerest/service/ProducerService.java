package pl.janiec.krystian.gamestorerest.service;

import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;

import java.util.List;

public interface ProducerService {

    List<ProducerDTO> getAllProducers();

    ProducerDTO getProducerById(Long id);

    ProducerDTO createProducer(ProducerDTO producerDTO);

    ProducerDTO updateProducer(ProducerDTO producerDTO, Long id);

    void deleteProducerById(Long id);

}
