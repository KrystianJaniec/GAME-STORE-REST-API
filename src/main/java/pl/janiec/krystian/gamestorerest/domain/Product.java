package pl.janiec.krystian.gamestorerest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    @Lob
    private String productDescription;
    private Double productPrice;
    private String categoryName;
    private Integer producerId;
}
