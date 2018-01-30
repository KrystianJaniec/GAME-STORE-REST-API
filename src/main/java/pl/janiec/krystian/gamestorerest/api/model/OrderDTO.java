package pl.janiec.krystian.gamestorerest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.janiec.krystian.gamestorerest.domain.OrderState;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    @JsonProperty("order_date")
    private String date;
    private Integer quantity;
    @JsonProperty("total_price")
    private Double totalPrice;
    private OrderState state;
    @JsonProperty("order_url")
    private String orderUrl;
    @JsonProperty("customer_url")
    private String customerUrl;
    @JsonProperty("product_url")
    private String productUrl;
}
