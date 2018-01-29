package pl.janiec.krystian.gamestorerest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.janiec.krystian.gamestorerest.api.model.CategoryDTO;
import pl.janiec.krystian.gamestorerest.api.model.CustomerDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProducerDTO;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.domain.Customer;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.domain.Product;

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

    public static Producer createNewProducer(String name, String shortcutName, Long id) {
        Producer producer = new Producer();
        producer.setCompanyName(name);
        producer.setCompanyShortcutName(shortcutName);
        producer.setId(id);
        return producer;
    }

    public static ProducerDTO createNewProducerDTO(String name, String shortcut) {
        ProducerDTO producer = new ProducerDTO();
        producer.setName(name);
        producer.setShortcut(shortcut);
        return producer;
    }

    public static Product createNewProduct(String name, String description, Double price, Long id) {
        Product product = new Product();
        product.setProductName(name);
        product.setProductDescription(description);
        product.setProductPrice(price);
        product.setId(id);
        return product;
    }

    public static Product createNewProduct(String name, String description, Double price, Integer producerId, String categoryName, Long id) {
        Product product = new Product();
        product.setProductName(name);
        product.setProductDescription(description);
        product.setProductPrice(price);
        product.setProducerId(producerId);
        product.setCategoryName(categoryName);
        product.setId(id);
        return product;
    }

    public static ProductDTO createNewProductDTO(String name, String description, Double price) {
        ProductDTO product = new ProductDTO();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    public static String writeValueAsJSON(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
