package pl.janiec.krystian.gamestorerest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.service.CustomerService;

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

import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void shouldSuccessfullyShowListWithAllCustomers() throws Exception {
        List<CustomerDTO> customerList = Arrays.asList(createNewCustomerDTO(ADAM, KOWALSKI), createNewCustomerDTO(ANNA, NOWAK));

        when(customerService.getAllCustomers()).thenReturn(customerList);

        mockMvc.perform(get(CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerList", hasSize(2)));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void shouldSuccessfullyShowCustomerWithThisId() throws Exception {
        CustomerDTO customer = createNewCustomerDTO(ANNA, NOWAK);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(equalTo(ANNA))))
                .andExpect(jsonPath("$.lastName", is(equalTo(NOWAK))));

        verify(customerService, times(1)).getCustomerById(ANNA_NOWAK_ID);
    }

    @Test
    public void shouldCreateNewCustomerFromCustomerDTOAndReturnNewCustomerDTO() throws Exception {
        CustomerDTO customerDTO = createNewCustomerDTO(ADAM, KOWALSKI);
        CustomerDTO newCustomerDTO = createNewCustomerDTO(customerDTO.getFirstName(), customerDTO.getLastName());

        when(customerService.createCustomer(customerDTO)).thenReturn(newCustomerDTO);

        mockMvc.perform(post(CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(equalTo(ADAM))))
                .andExpect(jsonPath("$.lastName", is(equalTo(KOWALSKI))));

        verify(customerService, times(1)).createCustomer(customerDTO);
    }

    @Test
    public void shouldUpdateCustomerFromCustomerDTOWithGivenIdAndReturnNewCustomerDTO() throws Exception {
        CustomerDTO customerDTO = createNewCustomerDTO(ANNA, NOWAK);
        CustomerDTO updatedCustomerDTO = createNewCustomerDTO(customerDTO.getFirstName(), customerDTO.getLastName());

        when(customerService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(updatedCustomerDTO);

        mockMvc.perform(put(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(equalTo(ANNA))))
                .andExpect(jsonPath("$.lastName", is(equalTo(NOWAK))));

        verify(customerService, times(1)).updateCustomer(customerDTO, ANNA_NOWAK_ID);
    }

    @Test
    public void shouldDeleteCustomerWithGivenId() throws Exception {
        mockMvc.perform(delete(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}
