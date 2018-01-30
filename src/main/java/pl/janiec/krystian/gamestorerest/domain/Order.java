package pl.janiec.krystian.gamestorerest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderDate;
    private Integer quantity;
    private Double totalPrice;
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;
    private Long customerId;
    private Long productId;
}
