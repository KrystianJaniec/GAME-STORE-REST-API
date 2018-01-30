package pl.janiec.krystian.gamestorerest.service;

import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(OrderDTO orderDTO, Long id);

    void deleteOrder(Long id);

    List<OrderDTO> getAllOrdersForCustomerWithId(Long id);

    OrderDTO createOrderForCustomerWithId(Long id, OrderDTO orderDTO);
}
