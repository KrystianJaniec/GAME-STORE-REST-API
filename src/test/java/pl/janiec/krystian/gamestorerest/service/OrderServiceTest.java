package pl.janiec.krystian.gamestorerest.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.janiec.krystian.gamestorerest.api.mapper.OrderMapper;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.domain.Order;
import pl.janiec.krystian.gamestorerest.domain.OrderState;
import pl.janiec.krystian.gamestorerest.repository.OrderRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static pl.janiec.krystian.gamestorerest.controller.CustomerController.CUSTOMERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.OrderController.ORDERS_URL;
import static pl.janiec.krystian.gamestorerest.controller.ProductController.PRODUCTS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewOrder;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewOrderDTO;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        orderService = new OrderServiceImpl(orderRepository, OrderMapper.INSTANCE);
    }

    @Test
    public void shouldSuccessfullyGetListWithAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> orderDTOList = orderService.getAllOrders();

        assertThat(orderDTOList.size(), is(equalTo(orders.size())));
    }

    @Test
    public void shouldSuccessfullyGetOrderByOrderId() throws Exception {
        Order order = createNewOrder(ORDER_ID, ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.ORDERED,
                ADAM_KOWALSKI_ID, MAFIA_ID);

        when(orderRepository.findOne(anyLong())).thenReturn(order);

        OrderDTO orderDTO = orderService.getOrderById(ORDER_ID);

        assertThat(orderDTO.getDate(), equalTo(ORDER_DATE));
        assertThat(orderDTO.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(orderDTO.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(orderDTO.getState(), equalTo(OrderState.ORDERED));
        assertThat(orderDTO.getOrderUrl(), equalTo(ORDERS_URL + ORDER_ID));
        assertThat(orderDTO.getCustomerUrl(), equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID));
        assertThat(orderDTO.getProductUrl(), equalTo(PRODUCTS_URL + MAFIA_ID));

    }

    @Test
    public void shouldSuccessfullyCreateNewOrder() throws Exception {
        OrderDTO orderDTO = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.CANCELED);
        Order order = createNewOrder(ORDER_ID, orderDTO.getDate(), orderDTO.getQuantity(), orderDTO.getTotalPrice(), orderDTO.getState(),
                ADAM_KOWALSKI_ID, MAFIA_ID);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO newOrderDTO = orderService.createOrder(orderDTO);

        assertThat(newOrderDTO.getDate(), equalTo(ORDER_DATE));
        assertThat(newOrderDTO.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(newOrderDTO.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(newOrderDTO.getState(), equalTo(OrderState.CANCELED));
        assertThat(newOrderDTO.getOrderUrl(), equalTo(ORDERS_URL + ORDER_ID));
        assertThat(newOrderDTO.getCustomerUrl(), equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID));
        assertThat(newOrderDTO.getProductUrl(), equalTo(PRODUCTS_URL + MAFIA_ID));
    }

    @Test
    public void shouldSuccessfullyUpdateOrder() throws Exception {
        OrderDTO orderDTO = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.ORDERED);
        Order order = createNewOrder(ORDER_ID, orderDTO.getDate(), orderDTO.getQuantity(), orderDTO.getTotalPrice(), orderDTO.getState(),
                ANNA_NOWAK_ID, WITCHER_ID);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO updatedOrderDTO = orderService.updateOrder(orderDTO, ORDER_ID);

        assertThat(updatedOrderDTO.getDate(), equalTo(ORDER_DATE));
        assertThat(updatedOrderDTO.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(updatedOrderDTO.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(updatedOrderDTO.getState(), equalTo(OrderState.ORDERED));
        assertThat(updatedOrderDTO.getOrderUrl(), equalTo(ORDERS_URL + ORDER_ID));
        assertThat(updatedOrderDTO.getCustomerUrl(), equalTo(CUSTOMERS_URL + ANNA_NOWAK_ID));
        assertThat(updatedOrderDTO.getProductUrl(), equalTo(PRODUCTS_URL + WITCHER_ID));
    }

    @Test
    public void shouldSuccessfullyDeleteOrder() throws Exception {
        orderService.deleteOrder(ORDER_ID);

        verify(orderRepository, times(1)).delete(ORDER_ID);
    }

    @Test
    public void shouldShowAllOrderForCustomerWithThisId() throws Exception {
        List<Order> orders = Collections.singletonList(createNewOrder(ORDER_ID, ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.ORDERED,
                ADAM_KOWALSKI_ID, MAFIA_ID));

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> orderDTOList = orderService.getAllOrdersForCustomerWithId(ADAM_KOWALSKI_ID);

        assertThat(orderDTOList.size(), is(equalTo(orders.size())));
        assertThat(orderDTOList.get(0).getDate(), equalTo(ORDER_DATE));
        assertThat(orderDTOList.get(0).getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(orderDTOList.get(0).getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(orderDTOList.get(0).getState(), equalTo(OrderState.ORDERED));
        assertThat(orderDTOList.get(0).getOrderUrl(), equalTo(ORDERS_URL + ORDER_ID));
        assertThat(orderDTOList.get(0).getCustomerUrl(), equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID));
        assertThat(orderDTOList.get(0).getProductUrl(), equalTo(PRODUCTS_URL + MAFIA_ID));
    }

    @Test
    public void shouldShowCreatedOrderForCustomerWithId() throws Exception {
        OrderDTO orderDTO = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.CANCELED);
        Order order = createNewOrder(ORDER_ID, orderDTO.getDate(), orderDTO.getQuantity(), orderDTO.getTotalPrice(), orderDTO.getState(),
                ADAM_KOWALSKI_ID, MAFIA_ID);
        List<Order> orders = Collections.singletonList(order);

        when(orderRepository.findAll()).thenReturn((orders));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO newOrderDTO = orderService.createOrderForCustomerWithId(ADAM_KOWALSKI_ID, orderDTO);

        assertThat(newOrderDTO.getDate(), equalTo(ORDER_DATE));
        assertThat(newOrderDTO.getQuantity(), equalTo(ORDER_QUANTITY));
        assertThat(newOrderDTO.getTotalPrice(), equalTo(ORDER_PRICE));
        assertThat(newOrderDTO.getState(), equalTo(OrderState.CANCELED));
        assertThat(newOrderDTO.getOrderUrl(), equalTo(ORDERS_URL + ORDER_ID));
        assertThat(newOrderDTO.getCustomerUrl(), equalTo(CUSTOMERS_URL + ADAM_KOWALSKI_ID));
        assertThat(newOrderDTO.getProductUrl(), equalTo(PRODUCTS_URL + MAFIA_ID));
    }
}
