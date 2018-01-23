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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static pl.janiec.krystian.gamestorerest.controller.ProducerController.PRODUCER_URL;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;

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
        List<ProducerDTO> producerList = Arrays.asList(createNewProducerDTO(CD_PROJEKT_RED, CDPR), createNewProducerDTO(TWO_K_SPORTS, TWO_K));

        when(producerService.getAllProducers()).thenReturn(producerList);

        mockMvc.perform(get(PRODUCER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producerList", hasSize(2)));

        verify(producerService, times(1)).getAllProducers();
    }

    @Test
    public void shouldSuccessfullyShowProducerWithThisID() throws Exception {
        ProducerDTO producer = createNewProducerDTO(CD_PROJEKT_RED, CDPR);

        when(producerService.getProducerById(anyLong())).thenReturn(producer);

        mockMvc.perform(get(PRODUCER_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))));

        verify(producerService, times(1)).getProducerById(CDPR_ID);
    }

    @Test
    public void shouldCreateNewProducerFromProducerDTO() throws Exception {
        ProducerDTO producerDTO = createNewProducerDTO(CD_PROJEKT_RED, CDPR);
        ProducerDTO newProducerDTO = createNewProducerDTO(producerDTO.getName(), producerDTO.getShortcut());

        when(producerService.createProducer(producerDTO)).thenReturn(newProducerDTO);

        mockMvc.perform(post(PRODUCER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(producerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))));

        verify(producerService, times(1)).createProducer(producerDTO);
    }

    @Test
    public void shouldUpdateProducerFromProducerDTOWithGivenID() throws Exception {
        ProducerDTO producerDTO = createNewProducerDTO(CD_PROJEKT_RED, CDPR);
        ProducerDTO updatedProducerDTO = createNewProducerDTO(producerDTO.getName(), producerDTO.getShortcut());

        when(producerService.updateProducer(any(ProducerDTO.class), anyLong())).thenReturn(updatedProducerDTO);

        mockMvc.perform(put(PRODUCER_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(producerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(equalTo(CD_PROJEKT_RED))))
                .andExpect(jsonPath("$.shortcut", is(equalTo(CDPR))));

        verify(producerService, times(1)).updateProducer(producerDTO, CDPR_ID);
    }

    @Test
    public void shouldDeleteProducerWithThisID() throws Exception {
        mockMvc.perform(delete(PRODUCER_URL + "{id}", CDPR_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(producerService).deleteProducerById(anyLong());
    }
}
