package pl.janiec.krystian.gamestorerest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.service.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.janiec.krystian.gamestorerest.controller.ProductController.PRODUCTS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProductDTO;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.writeValueAsJSON;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void shouldSuccessfullyShowListWithAllProducts() throws Exception {
        List<ProductDTO> products = Arrays.asList(createNewProductDTO(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE),
                createNewProductDTO(MAFIA_II, MAFIA_DESCRIPTION, MAFIA_PRICE));

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get(PRODUCTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void shouldSuccessfullyShowProductWithThisId() throws Exception {
        ProductDTO productDTO = createNewProductDTO(MAFIA_II, MAFIA_DESCRIPTION, MAFIA_PRICE);

        when(productService.getProductById(anyLong())).thenReturn(productDTO);

        mockMvc.perform(get(PRODUCTS_URL + "{id}", MAFIA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(MAFIA_II)))
                .andExpect(jsonPath("$.description", equalTo(MAFIA_DESCRIPTION)))
                .andExpect(jsonPath("$.price", equalTo(MAFIA_PRICE)));
    }

    @Test
    public void shouldSuccessfullyCreateAndShowNewProduct() throws Exception {
        ProductDTO productContent = createNewProductDTO(MAFIA_II, MAFIA_DESCRIPTION, MAFIA_PRICE);
        ProductDTO productDTO = createNewProductDTO(productContent.getName(), productContent.getDescription(), productContent.getPrice());

        when(productService.createProduct(productContent)).thenReturn(productDTO);

        mockMvc.perform(post(PRODUCTS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(productContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(MAFIA_II)))
                .andExpect(jsonPath("$.description", equalTo(MAFIA_DESCRIPTION)))
                .andExpect(jsonPath("$.price", equalTo(MAFIA_PRICE)));
    }

    @Test
    public void shouldUpdateProductWithGivenIdAndShowUpdatedDTO() throws Exception {
        ProductDTO productContent = createNewProductDTO(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE);
        ProductDTO productDTO = createNewProductDTO(productContent.getName(), productContent.getDescription(), productContent.getPrice());

        when(productService.updateProduct(any(ProductDTO.class), anyLong())).thenReturn(productDTO);

        mockMvc.perform(put(PRODUCTS_URL + "{id}", WITCHER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(productContent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(WITCHER_3)))
                .andExpect(jsonPath("$.description", equalTo(WITCHER_DESCRIPTION)))
                .andExpect(jsonPath("$.price", equalTo(WITCHER_PRICE)));
    }

    @Test
    public void shouldDeleteProductWithThisId() throws Exception {
        mockMvc.perform(delete(PRODUCTS_URL + "{id}", MAFIA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(anyLong());
    }
}
