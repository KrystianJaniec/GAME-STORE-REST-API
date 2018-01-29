package pl.janiec.krystian.gamestorerest.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.gamestorerest.api.mapper.CustomerMapper;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.domain.Customer;
import pl.janiec.krystian.gamestorerest.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewCustomer;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewCustomerDTO;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void shouldSuccessfullyGetListWithAllCustomers() throws Exception {
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        assertThat(customerDTOList.size(), is(equalTo(customerList.size())));
    }

    @Test
    public void shouldSuccessfullyGetCustomerById() throws Exception {
        Customer customer = createNewCustomer(ADAM, KOWALSKI, ADAM_KOWALSKI_ID);

        when(customerRepository.findById(anyLong())).thenReturn(customer);

        CustomerDTO customerDTO = customerService.getCustomerById(ADAM_KOWALSKI_ID);

        assertThat(customerDTO.getFirstName(), is(equalTo(ADAM)));
        assertThat(customerDTO.getLastName(), is(equalTo(KOWALSKI)));
        assertThat(customerDTO.getCustomerUrl(), is(equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID)));
    }

    @Test
    public void shouldCreateNewCustomerAndReturnChangedCustomerDTO() throws Exception {
        CustomerDTO customerDTO = createNewCustomerDTO(ADAM, KOWALSKI);
        Customer savedCustomerInDB = createNewCustomer(customerDTO.getFirstName(), customerDTO.getLastName(), ADAM_KOWALSKI_ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomerInDB);

        CustomerDTO newCustomerDTO = customerService.createCustomer(customerDTO);

        assertThat(newCustomerDTO.getFirstName(), is(equalTo(customerDTO.getFirstName())));
        assertThat(newCustomerDTO.getLastName(), is(equalTo(customerDTO.getLastName())));
        assertThat(newCustomerDTO.getCustomerUrl(), is(equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID)));
    }

    @Test
    public void shouldUpdateCustomerAndReturnUpdatedCustomerDTO() throws Exception {
        CustomerDTO customerDTO = createNewCustomerDTO(ADAM, KOWALSKI);
        Customer savedCustomerInDB = createNewCustomer(customerDTO.getFirstName(), customerDTO.getLastName(), ADAM_KOWALSKI_ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomerInDB);

        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(customerDTO, ADAM_KOWALSKI_ID);

        assertThat(updatedCustomerDTO.getFirstName(), is(equalTo(customerDTO.getFirstName())));
        assertThat(updatedCustomerDTO.getLastName(), is(equalTo(customerDTO.getLastName())));
        assertThat(updatedCustomerDTO.getCustomerUrl(), is(equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID)));
    }

    @Test
    public void shouldSuccessfullyDeleteCustomerWithThisId() throws Exception {
        customerService.deleteCustomerById(ANNA_NOWAK_ID);

        verify(customerRepository, times(1)).delete(ANNA_NOWAK_ID);
    }
}
