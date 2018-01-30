package pl.janiec.krystian.gamestorerest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.janiec.krystian.gamestorerest.api.model.OrderDTO;
import pl.janiec.krystian.gamestorerest.domain.OrderState;
import pl.janiec.krystian.gamestorerest.service.OrderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.janiec.krystian.gamestorerest.controller.OrderController.ORDERS_URL;
import static pl.janiec.krystian.gamestorerest.util.TestConstants.*;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.createNewOrderDTO;
import static pl.janiec.krystian.gamestorerest.util.TestUtil.writeValueAsJSON;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void shouldSuccessfullyShowListWithAllOrders() throws Exception {
        List<OrderDTO> orders = Arrays.asList(createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.ORDERED),
                createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.CREATED));

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get(ORDERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders", hasSize(2)));
    }

    @Test
    public void shouldSuccessfullyShowOrderWithThisId() throws Exception {
        OrderDTO orderDTO = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.CREATED);

        when(orderService.getOrderById(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(get(ORDERS_URL + "{id}", ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_date", equalTo(ORDER_DATE)))
                .andExpect(jsonPath("$.quantity", equalTo(ORDER_QUANTITY)))
                .andExpect(jsonPath("$.total_price", equalTo(ORDER_PRICE)))
                .andExpect(jsonPath("$.state", equalTo(OrderState.CREATED.toString())));
    }

    @Test
    public void shouldSuccessfullyCreateAndShowOrder() throws Exception {
        OrderDTO orderContent = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.ORDERED);
        OrderDTO orderDTO = createNewOrderDTO(orderContent.getDate(), orderContent.getQuantity(), orderContent.getTotalPrice(), orderContent.getState());

        when(orderService.createOrder(orderContent)).thenReturn(orderDTO);

        mockMvc.perform(post(ORDERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(orderContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.order_date", equalTo(ORDER_DATE)))
                .andExpect(jsonPath("$.quantity", equalTo(ORDER_QUANTITY)))
                .andExpect(jsonPath("$.total_price", equalTo(ORDER_PRICE)))
                .andExpect(jsonPath("$.state", equalTo(OrderState.ORDERED.toString())));
    }

    @Test
    public void shouldUpdateOrderAndShowUpdatedDTO() throws Exception {
        OrderDTO orderContent = createNewOrderDTO(ORDER_DATE, ORDER_QUANTITY, ORDER_PRICE, OrderState.CANCELED);
        OrderDTO orderDTO = createNewOrderDTO(orderContent.getDate(), orderContent.getQuantity(), orderContent.getTotalPrice(), orderContent.getState());

        when(orderService.updateOrder(orderContent, ORDER_ID)).thenReturn(orderDTO);

        mockMvc.perform(put(ORDERS_URL + "{id}", ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsJSON(orderContent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_date", equalTo(ORDER_DATE)))
                .andExpect(jsonPath("$.quantity", equalTo(ORDER_QUANTITY)))
                .andExpect(jsonPath("$.total_price", equalTo(ORDER_PRICE)))
                .andExpect(jsonPath("$.state", equalTo(OrderState.CANCELED.toString())));
    }

    @Test
    public void shouldDeleteOrderWithThisId() throws Exception {
        mockMvc.perform(delete(ORDERS_URL + "{id}", MAFIA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(anyLong());
    }
}
