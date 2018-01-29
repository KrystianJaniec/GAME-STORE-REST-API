package pl.janiec.krystian.gamestorerest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    @JsonProperty("product_url")
    private String productUrl;
    @JsonProperty("producer_url")
    private String producerUrl;
    @JsonProperty("category_url")
    private String categoryUrl;
}
