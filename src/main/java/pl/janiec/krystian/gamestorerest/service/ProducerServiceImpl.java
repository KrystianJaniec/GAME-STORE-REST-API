package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.ProducerMapper;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.repository.ProducerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;

    @Autowired
    public ProducerServiceImpl(ProducerRepository producerRepository, ProducerMapper producerMapper) {
        this.producerRepository = producerRepository;
        this.producerMapper = producerMapper;
    }

    @Override
    public List<ProducerDTO> getAllProducers() {
        return producerRepository.findAll()
                .stream()
                .map(producerMapper::producerToProducerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProducerDTO getProducerById(Long id) {
        return producerMapper.producerToProducerDTO(producerRepository.findById(id));
    }

    @Override
    public ProducerDTO createProducer(ProducerDTO producerDTO) {
        Producer producer = producerMapper.producerDTOToProducer(producerDTO);
        Producer savedProducerInDB = producerRepository.save(producer);

        return producerMapper.producerToProducerDTO(savedProducerInDB);
    }

    @Override
    public ProducerDTO updateProducer(ProducerDTO producerDTO, Long id) {
        Producer producer = producerMapper.producerDTOToProducer(producerDTO);
        producer.setId(id);
        Producer savedProducerInDB = producerRepository.save(producer);

        return producerMapper.producerToProducerDTO(savedProducerInDB);
    }

    @Override
    public void deleteProducerById(Long id) {
        producerRepository.delete(id);
    }
}
