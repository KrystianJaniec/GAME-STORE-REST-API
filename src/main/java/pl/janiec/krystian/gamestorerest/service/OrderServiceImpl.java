package pl.janiec.krystian.gamestorerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiec.krystian.gamestorerest.api.mapper.OrderMapper;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.domain.Order;
import pl.janiec.krystian.gamestorerest.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.janiec.krystian.gamestorerest.controller.OrderController.ORDERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProductController.PRODUCTS_URL;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
                    setOrderDtoUrls(orderDTO,order);

                    return orderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findOne(id);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        setOrderDtoUrls(orderDTO,order);

        return orderDTO;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return saveOrderAndReturnOrderDTO(orderMapper.orderDTOtoOrder(orderDTO));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Order order = orderMapper.orderDTOtoOrder(orderDTO);
        order.setId(id);

        return saveOrderAndReturnOrderDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }

    @Override
    public List<OrderDTO> getAllOrdersForCustomerWithId(Long id) {
        return orderRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerId().equals(id))
                .map(order -> {
                    OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
                    setOrderDtoUrls(orderDTO,order);

                    return orderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO createOrderForCustomerWithId(Long id, OrderDTO orderDTO) {
        return orderRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerId().equals(id))
                .map(this::saveOrderAndReturnOrderDTO)
                .findAny().orElseGet(null);
    }

    private OrderDTO saveOrderAndReturnOrderDTO(Order order){
        Order orderInDB = orderRepository.save(order);
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(orderInDB);

        setOrderDtoUrls(orderDTO,orderInDB);

        return orderDTO;
    }

    private void setOrderDtoUrls(OrderDTO orderDTO, Order order){
        orderDTO.setOrderUrl(ORDERS_URL + order.getId());
        orderDTO.setCustomerUrl(CUSTOMERS_URL + order.getCustomerId());
        orderDTO.setProductUrl(PRODUCTS_URL + order.getProductId());
    }
}
