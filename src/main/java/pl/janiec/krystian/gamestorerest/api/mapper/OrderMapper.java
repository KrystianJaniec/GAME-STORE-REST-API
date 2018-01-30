package pl.janiec.krystian.gamestorerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.domain.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mappings({
            @Mapping(source = "orderDate", target = "date"),
            @Mapping(source = "orderState", target = "state")})
    OrderDTO orderToOrderDTO(Order order);

    @Mappings({
            @Mapping(source = "date", target = "orderDate"),
            @Mapping(source = "state", target = "orderState")})
    Order orderDTOtoOrder(OrderDTO orderDTO);

}
