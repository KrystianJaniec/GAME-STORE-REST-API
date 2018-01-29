package pl.janiec.krystian.gamestorerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(source = "firstName", target = "firstName")
    Customer customerDTOtoCustomer(CustomerDTO customerDTO);
}
