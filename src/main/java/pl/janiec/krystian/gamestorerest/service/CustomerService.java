package pl.janiec.krystian.gamestorerest.service;

import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);

    void deleteCustomerById(Long id);
}
