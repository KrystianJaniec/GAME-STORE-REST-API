package pl.janiec.krystian.gamestorerest.api.mapper;

import org.junit.Test;
import pl.janiec.krystian.gamestorerest.api.model.ProductDTO;
import pl.janiec.krystian.gamestorerest.domain.Product;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProduct;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewProductDTO;

public class ProductMapperTest {

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    public void shouldMappingProductToProductDTO() throws Exception {
        Product product = createNewProduct(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE, WITCHER_ID);

        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertThat(productDTO.getName(), is(equalTo(WITCHER_3)));
        assertThat(productDTO.getDescription(), is(equalTo(WITCHER_DESCRIPTION)));
        assertThat(productDTO.getPrice(), is(equalTo(WITCHER_PRICE)));
    }

    @Test
    public void shouldMappingProductDTOToProduct() throws Exception {
        ProductDTO productDTO = createNewProductDTO(WITCHER_3, WITCHER_DESCRIPTION, WITCHER_PRICE);

        Product product = productMapper.productDTOtoProduct(productDTO);

        assertThat(product.getProductName(), is(equalTo(WITCHER_3)));
        assertThat(product.getProductDescription(), is(equalTo(WITCHER_DESCRIPTION)));
        assertThat(product.getProductPrice(), is(equalTo(WITCHER_PRICE)));
    }
}
