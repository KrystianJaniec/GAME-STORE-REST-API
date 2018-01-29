package pl.janiec.krystian.gamestorerest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.service.ProducerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.janiec.krystian.gamestorerest.controller.ProducerController.PRODUCERS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProducerDTO;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.writeValueAsJSON;

public class ProducerControllerTest {

    @Mock
    private ProducerService producerService;

    @InjectMocks
    private ProducerController producerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(producerController).build();
    }

    @Test
    public void shouldSuccessfullyShowListWithAllProducers() throws Exception {
        List<ProducerDTO> producers = Arrays.asList(createNewProducerDTO(CD_PROJEKT_RED, CDPR), createNewProducerDTO(TWO_K_SPORTS, TWO_K));

        when(producerService.getAllProducers()).thenReturn(producers);

        mockMvc.perform(get(PRODUCERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producers", hasSize(2)));

        verify(producerService, times(1)).getAllProducers();
    }

    @Test
    public void shouldSuccessfullyShowProducerWithThisID() throws Exception {
        ProducerDTO producer = createNewProducerDTO(CD_PROJEKT_RED, CDPR);
        producer.setProducerUrl(PRODUCERS_URL + CDPR_ID);

        when(producerService.getProducerById(anyLong())).thenReturn(producer);

        mockMvc.perform(get(PRODUCERS_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))))
                .andExpect(jsonPath("$.producer_url", is(equalTo(PRODUCERS_URL + CDPR_ID))));

        verify(producerService, times(1)).getProducerById(CDPR_ID);
    }

    @Test
    public void shouldCreateNewProducerFromProducerDTO() throws Exception {
        ProducerDTO producerContent = createNewProducerDTO(CD_PROJEKT_RED, CDPR);
        ProducerDTO producerDTO = createNewProducerDTO(producerContent.getName(), producerContent.getShortcut());
        producerDTO.setProducerUrl(PRODUCERS_URL + CDPR_ID);

        when(producerService.createProducer(producerContent)).thenReturn(producerDTO);

        mockMvc.perform(post(PRODUCERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(producerContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))))
                .andExpect(jsonPath("$.producer_url", is(equalTo(PRODUCERS_URL + CDPR_ID))));

        verify(producerService, times(1)).createProducer(producerContent);
    }

    @Test
    public void shouldUpdateProducerFromProducerDTOWithGivenId() throws Exception {
        ProducerDTO producerContent = createNewProducerDTO(CD_PROJEKT_RED, CDPR);
        ProducerDTO producerDTO = createNewProducerDTO(producerContent.getName(), producerContent.getShortcut());
        producerDTO.setProducerUrl(PRODUCERS_URL + CDPR_ID);

        when(producerService.updateProducer(any(ProducerDTO.class), anyLong())).thenReturn(producerDTO);

        mockMvc.perform(put(PRODUCERS_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(producerContent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))))
                .andExpect(jsonPath("$.producer_url", is(equalTo(PRODUCERS_URL + CDPR_ID))));

        verify(producerService, times(1)).updateProducer(producerContent, CDPR_ID);
    }

    @Test
    public void shouldDeleteProducerWithThisId() throws Exception {
        mockMvc.perform(delete(PRODUCERS_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(producerService, times(1)).deleteProducerById(anyLong());
    }
}
