package pl.janiec.krystian.gamestorerest.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.gamestorerest.api.mapper.ProducerMapper;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.repository.ProducerRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;

public class ProducerServiceTest {

    @Mock
    private ProducerRepository producerRepository;

    private ProducerService producerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        producerService = new ProducerServiceImpl(producerRepository, ProducerMapper.INSTANCE);
    }

    @Test
    public void shouldSuccessfullyGetListWithAllProducers() throws Exception{
        List<Producer> producerList = Arrays.asList(new Producer(), new Producer(), new Producer());

        when(producerRepository.findAll()).thenReturn(producerList);

        List<ProducerDTO> producerDTOList = producerService.getAllProducers();

        assertThat(producerDTOList.size(), is(equalTo(producerList.size())));
    }

    @Test
    public void shouldSuccessfullyGetProducerById() throws Exception {
        Producer producer = createNewProducer(CD_PROJEKT_RED, CDPR);

        when(producerRepository.findById(anyLong())).thenReturn(producer);

        ProducerDTO producerDTO = producerService.getProducerById(CDPR_ID);

        assertThat(producerDTO.getName(), is(equalTo(CD_PROJEKT_RED)));
        assertThat(producerDTO.getShortcut(), is(equalTo(CDPR)));
    }

    @Test
    public void shouldSuccessfullyCreateNewProducer() throws Exception{
        ProducerDTO producerDTO = createNewProducerDTO(TWO_K_SPORTS, TWO_K);
        Producer savedProducerInDB = createNewProducer(producerDTO.getName(), producerDTO.getShortcut());

        when(producerRepository.save(any(Producer.class))).thenReturn(savedProducerInDB);

        ProducerDTO newProducerDTO = producerService.createProducer(producerDTO);

        assertThat(newProducerDTO.getName(), is(equalTo(TWO_K_SPORTS)));
        assertThat(newProducerDTO.getShortcut(), is(equalTo(TWO_K)));
    }

    @Test
    public void shouldSuccessfullyUpdateProducer() throws Exception{
        ProducerDTO producerDTO = createNewProducerDTO(TWO_K_SPORTS, TWO_K);
        Producer savedProducerInDB = createNewProducer(producerDTO.getName(), producerDTO.getShortcut());

        when(producerRepository.save(any(Producer.class))).thenReturn(savedProducerInDB);

        ProducerDTO updatedProducerDTO = producerService.updateProducer(producerDTO,TWO_K_ID);

        assertThat(updatedProducerDTO.getName(), is(equalTo(TWO_K_SPORTS)));
        assertThat(updatedProducerDTO.getShortcut(), is(equalTo(TWO_K)));
    }

    @Test
    public void shouldSuccessfullyDeleteProducerWithThisId() throws Exception {
        producerService.deleteProducerById(TWO_K_ID);

        verify(producerRepository, times(1)).delete(TWO_K_ID);
    }
}
