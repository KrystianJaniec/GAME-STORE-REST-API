package pl.janiec.krystian.gamestorerest.api.mapper;

import org.junit.Test;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.domain.Order;
import pl.janiec.krystian.gamestorerest.domain.OrderState;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.*;

public class OrderMapperTest {

    private OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    public void shouldMappingOrderToOrderDTO() throws Exception{
        Order order = createNewOrder(ORDER_ID,ORDER_DATE,ORDER_QUANTITY,ORDER_PRICE, OrderState.CREATED,ANNA_NOWAK_ID,WITCHER_ID);

        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);

        assertThat(orderDTO.getId(), equalTo(ORDER_ID));
        assertThat(orderDTO.getDate(), equalTo(ORDER_DATE));
        assertThat(orderDTO.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(orderDTO.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(orderDTO.getState(), equalTo(OrderState.CREATED));
    }

    @Test
    public void shouldMappingOrderDTOtoOrder() throws Exception{
        OrderDTO orderDTO = createNewOrderDTO(ORDER_DATE,ORDER_QUANTITY,ORDER_PRICE, OrderState.CREATED);

        Order order = orderMapper.orderDTOtoOrder(orderDTO);

        assertThat(order.getOrderDate(), equalTo(ORDER_DATE));
        assertThat(order.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(order.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(order.getOrderState(), equalTo(OrderState.CREATED));
    }
}
