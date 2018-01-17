package pl.janiec.krystian.gamestorerest.api.mapper;

import org.junit.Test;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.domain.Customer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;

public class CustomerMapperTest {

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void shouldMappingCustomerClassToCustomerDTO() throws Exception {
        Customer customer = createNewCustomer(ANNA, NOWAK, ANNA_NOWAK_ID);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertThat(customerDTO.getFirstName(), is(equalTo(ANNA)));
        assertThat(customerDTO.getLastName(), is(equalTo(NOWAK)));
    }
}
