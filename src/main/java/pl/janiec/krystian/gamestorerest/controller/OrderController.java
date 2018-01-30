package pl.janiec.krystian.gamestorerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.api.model.OrderListDTO;
import pl.janiec.krystian.gamestorerest.service.OrderService;

@RestController
@RequestMapping(OrderController.ORDERS_URL)
public class OrderController {

    public static final String ORDERS_URL = "/api/orders/";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderListDTO getAllOrders() {
        return new OrderListDTO(orderService.getAllOrders());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        return orderService.updateOrder(orderDTO, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
