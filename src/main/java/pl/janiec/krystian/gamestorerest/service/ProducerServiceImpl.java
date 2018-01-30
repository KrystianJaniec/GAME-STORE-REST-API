package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.ProducerMapper;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.repository.ProducerRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.janiec.krystian.gamestorerest.controller.ProducerController.PRODUCERS_URL;

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
                .map(producer -> {
                    ProducerDTO producerDTO = producerMapper.producerToProducerDTO(producer);
                    setProducerDtoUrl(producerDTO,producer);

                    return producerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProducerDTO getProducerById(Long id) {
        ProducerDTO producerDTO = producerMapper.producerToProducerDTO(producerRepository.findById(id));
        producerDTO.setProducerUrl(PRODUCERS_URL + id);

        return producerDTO;
    }

    @Override
    public ProducerDTO createProducer(ProducerDTO producerDTO) {
        return saveProducerAndReturnProducerDTO(producerMapper.producerDTOtoProducer(producerDTO));
    }

    @Override
    public ProducerDTO updateProducer(ProducerDTO producerDTO, Long id) {
        Producer producer = producerMapper.producerDTOtoProducer(producerDTO);
        producer.setId(id);

        return saveProducerAndReturnProducerDTO(producer);
    }

    @Override
    public void deleteProducerById(Long id) {
        producerRepository.delete(id);
    }

    private ProducerDTO saveProducerAndReturnProducerDTO(Producer producer) {
        Producer producerInDB = producerRepository.save(producer);
        ProducerDTO producerDTO = producerMapper.producerToProducerDTO(producerInDB);

        setProducerDtoUrl(producerDTO,producerInDB);

        return producerDTO;
    }

    private void setProducerDtoUrl(ProducerDTO producerDTO, Producer producer){
        producerDTO.setProducerUrl(PRODUCERS_URL + producer.getId());
    }
}
