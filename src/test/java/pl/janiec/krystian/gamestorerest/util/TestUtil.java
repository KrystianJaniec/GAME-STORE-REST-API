package pl.janiec.krystian.gamestorerest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.domain.Customer;

public class TestUtil {

    public static Category createNewCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    public static CategoryDTO createNewCategoryDTO(Long id, String name) {
        CategoryDTO category = new CategoryDTO();
        category.setId(id);
        category.setName(name);
        return category;
    }

    public static Customer createNewCustomer(String firstName, String lastName, Long id) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setId(id);
        return customer;
    }

    public static CustomerDTO createNewCustomerDTO(String firstName, String lastName) {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    public static String writeValueAsJSON(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
