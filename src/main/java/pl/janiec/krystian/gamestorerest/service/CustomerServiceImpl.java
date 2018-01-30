package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.CustomerMapper;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.domain.Customer;
import pl.janiec.krystian.gamestorerest.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    setCustomerDtoUrls(customerDTO,customer);

                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findById(id));
        customerDTO.setCustomerUrl(CUSTOMERS_URL + id);

        return customerDTO;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return saveCustomerAndReturnCustomerDTO(customerMapper.customerDTOtoCustomer(customerDTO));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);

        return saveCustomerAndReturnCustomerDTO(customer);
    }

    private CustomerDTO saveCustomerAndReturnCustomerDTO(Customer customer) {
        Customer customerInDB = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerInDB);

        setCustomerDtoUrls(customerDTO,customerInDB);

        return customerDTO;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.delete(id);
    }

    private void setCustomerDtoUrls(CustomerDTO customerDTO, Customer customer){
        customerDTO.setCustomerUrl(CUSTOMERS_URL + customer.getId());
    }
}
