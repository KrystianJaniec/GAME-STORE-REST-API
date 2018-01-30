package pl.janiec.krystian.gamestorerest.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.gamestorerest.api.mapper.ProductMapper;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.domain.Product;
import pl.janiec.krystian.gamestorerest.repository.ProductRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static pl.janiec.krystian.gamestorerest.controller.CategoryController.CATEGORIES_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProducerController.PRODUCERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProductController.PRODUCTS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProduct;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProductDTO;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(ProductMapper.INSTANCE, productRepository);
    }

    @Test
    public void shouldSuccessfullyGetListWithAllProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> productDTOList = productService.getAllProducts();

        assertThat(productDTOList.size(), is(equalTo(products.size())));
    }

    @Test
    public void shouldSuccessfullyGetProductByProductName() throws Exception {
        Product product = createNewProduct(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE, WITCHER_ID);

        when(productRepository.findById(anyLong())).thenReturn(product);

        ProductDTO productDTO = productService.getProductById(WITCHER_ID);

        assertThat(productDTO.getName(), equalTo(WITCHER_3));
        assertThat(productDTO.getDescription(), equalTo(WITCHER_DESCRIPTION));
        assertThat(productDTO.getPrice(), equalTo(WITCHER_PRICE));
        assertThat(productDTO.getProductUrl(), equalTo(PRODUCTS_URL + WITCHER_ID));
    }

    @Test
    public void shouldSuccessfullyCreateNewProduct() throws Exception {
        ProductDTO productDTO = createNewProductDTO(MAFIA_II, MAFIA_DESCRIPTION, MAFIA_PRICE);
        Product savedProductInDB = createNewProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                PRODUCER_ID, CATEGORY_ACTION, MAFIA_ID);

        when(productRepository.save(any(Product.class))).thenReturn(savedProductInDB);

        ProductDTO newProductDTO = productService.createProduct(productDTO);

        assertThat(newProductDTO.getName(), equalTo(MAFIA_II));
        assertThat(newProductDTO.getDescription(), equalTo(MAFIA_DESCRIPTION));
        assertThat(newProductDTO.getPrice(), equalTo(MAFIA_PRICE));
        assertThat(newProductDTO.getProductUrl(), equalTo(PRODUCTS_URL + MAFIA_ID));
        assertThat(newProductDTO.getCategoryUrl(), equalTo(CATEGORIES_URL + CATEGORY_ACTION));
        assertThat(newProductDTO.getProducerUrl(), equalTo(PRODUCERS_URL + PRODUCER_ID));
    }

    @Test
    public void shouldSuccessfullyUpdateProduct() throws Exception {
        ProductDTO productDTO = createNewProductDTO(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE);
        Product product = createNewProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                PRODUCER_ID, CATEGORY_ACTION, WITCHER_ID);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedProductDTO = productService.updateProduct(productDTO, WITCHER_ID);

        assertThat(updatedProductDTO.getName(), equalTo(WITCHER_3));
        assertThat(updatedProductDTO.getDescription(), equalTo(WITCHER_DESCRIPTION));
        assertThat(updatedProductDTO.getPrice(), equalTo(WITCHER_PRICE));
        assertThat(updatedProductDTO.getProductUrl(), equalTo(PRODUCTS_URL + WITCHER_ID));
        assertThat(updatedProductDTO.getCategoryUrl(), equalTo(CATEGORIES_URL + CATEGORY_ACTION));
        assertThat(updatedProductDTO.getProducerUrl(), equalTo(PRODUCERS_URL + PRODUCER_ID));
    }

    @Test
    public void shouldSuccessfullyDeleteProductWithThisId() throws Exception {
        productService.deleteProduct(WITCHER_ID);

        verify(productRepository, times(1)).delete(WITCHER_ID);
    }

    @Test
    public void shouldShowAllProductsForProducerWithThisId() throws Exception {
        List<Product> products = Collections.singletonList(createNewProduct(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE,
                PRODUCER_ID, CATEGORY_ACTION, WITCHER_ID));

        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> productDTOList = productService.getAllProductsForProducerWithId(PRODUCER_ID);

        assertThat(productDTOList.size(), is(equalTo(products.size())));
        assertThat(productDTOList.get(0).getName(), equalTo(WITCHER_3));
        assertThat(productDTOList.get(0).getDescription(), equalTo(WITCHER_DESCRIPTION));
        assertThat(productDTOList.get(0).getPrice(), equalTo(WITCHER_PRICE));
        assertThat(productDTOList.get(0).getProductUrl(), equalTo(PRODUCTS_URL + WITCHER_ID));
        assertThat(productDTOList.get(0).getCategoryUrl(), equalTo(CATEGORIES_URL + CATEGORY_ACTION));
        assertThat(productDTOList.get(0).getProducerUrl(), equalTo(PRODUCERS_URL + PRODUCER_ID));
    }

    @Test
    public void shouldShowCreatedProductForProducerWithId() throws Exception {
        ProductDTO productDTO = createNewProductDTO(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE);
        Product product = createNewProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                PRODUCER_ID, CATEGORY_ACTION, WITCHER_ID);
        List<Product> products = Collections.singletonList(product);

        when(productRepository.findAll()).thenReturn((products));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO newProductDTO = productService.createProductForProducerWithId(PRODUCER_ID, productDTO);

        assertThat(newProductDTO.getName(), equalTo(WITCHER_3));
        assertThat(newProductDTO.getDescription(), equalTo(WITCHER_DESCRIPTION));
        assertThat(newProductDTO.getPrice(), equalTo(WITCHER_PRICE));
        assertThat(newProductDTO.getProductUrl(), equalTo(PRODUCTS_URL + WITCHER_ID));
        assertThat(newProductDTO.getCategoryUrl(), equalTo(CATEGORIES_URL + CATEGORY_ACTION));
        assertThat(newProductDTO.getProducerUrl(), equalTo(PRODUCERS_URL + PRODUCER_ID));
    }
}