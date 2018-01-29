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

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewCustomerDTO;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.writeValueAsJSON;

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
        List<CustomerDTO> customers = Arrays.asList(createNewCustomerDTO(ADAM, KOWALSKI), createNewCustomerDTO(ANNA, NOWAK));

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void shouldSuccessfullyShowCustomerWithThisId() throws Exception {
        CustomerDTO customer = createNewCustomerDTO(ANNA, NOWAK);
        customer.setCustomerUrl(CUSTOMERS_URL + ANNA_NOWAK_ID);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", is(equalTo(ANNA))))
                .andExpect(jsonPath("$.last_name", is(equalTo(NOWAK))))
                .andExpect(jsonPath("$.customer_url", is(equalTo(CUSTOMERS_URL + ANNA_NOWAK_ID))));

        verify(customerService, times(1)).getCustomerById(ANNA_NOWAK_ID);
    }

    @Test
    public void shouldCreateNewCustomerFromCustomerDTOAndShowNewCustomerDTO() throws Exception {
        CustomerDTO customerContent = createNewCustomerDTO(ADAM, KOWALSKI);
        CustomerDTO customerDTO = createNewCustomerDTO(customerContent.getFirstName(), customerContent.getLastName());
        customerDTO.setCustomerUrl(CUSTOMERS_URL + ADAM_KOWALSKI_ID);

        when(customerService.createCustomer(customerContent)).thenReturn(customerDTO);

        mockMvc.perform(post(CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(customerContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", is(equalTo(ADAM))))
                .andExpect(jsonPath("$.last_name", is(equalTo(KOWALSKI))))
                .andExpect(jsonPath("$.customer_url", is(equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID))));

        verify(customerService, times(1)).createCustomer(customerContent);
    }

    @Test
    public void shouldUpdateCustomerFromCustomerDTOWithGivenIdAndReturnNewCustomerDTO() throws Exception {
        CustomerDTO customerContent = createNewCustomerDTO(ANNA, NOWAK);
        CustomerDTO customerDTO = createNewCustomerDTO(customerContent.getFirstName(), customerContent.getLastName());
        customerDTO.setCustomerUrl(CUSTOMERS_URL + ANNA_NOWAK_ID);

        when(customerService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(customerDTO);

        mockMvc.perform(put(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(customerContent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", is(equalTo(ANNA))))
                .andExpect(jsonPath("$.last_name", is(equalTo(NOWAK))))
                .andExpect(jsonPath("$.customer_url", is(equalTo(CUSTOMERS_URL + ANNA_NOWAK_ID))));

        verify(customerService, times(1)).updateCustomer(customerContent, ANNA_NOWAK_ID);
    }

    @Test
    public void shouldDeleteCustomerWithGivenId() throws Exception {
        mockMvc.perform(delete(CUSTOMERS_URL + "{id}", ANNA_NOWAK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}
