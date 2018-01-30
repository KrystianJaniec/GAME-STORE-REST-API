package pl.janiec.krystian.gamestorerest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderListDTO {

    List<OrderDTO> orders;
}
