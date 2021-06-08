package be.vdab.toyshaydar.services;

import be.vdab.toyshaydar.domain.*;
import be.vdab.toyshaydar.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultOrderServiceTest {
    private DefaultOrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    private Order order;
    private OrderDetail orderDetail;
    private OrderDetail orderDetail2;
    private Product product;
    private Product product2;
    private Customer customer;

    @BeforeEach
    void beforeEach() {
        orderService = new DefaultOrderService(orderRepository);
        product = new Product("test", "test", "test", 1000, 500, BigDecimal.TEN);
        orderDetail = new OrderDetail(product, 200, BigDecimal.TEN);
        product2 = new Product("test", "test", "test", 500, 800, BigDecimal.TEN);
        orderDetail2 = new OrderDetail(product2, 600, BigDecimal.TEN);
        customer = new Customer("test", new Adress("test","test","test","test"), new Country("test"));
        order = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", OrderStatus.PROCESSING, customer);
        order.addOrderDetail(orderDetail);
        order.addOrderDetail(orderDetail2);
    }

    @Test
    void setAsShippped() {
        when(orderRepository.findOrderById(1)).thenReturn(Optional.of(order));
        try {
            orderService.setAsShippped(1);
        } catch (Exception e) {
        }
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    void findOrdersToBeShip() {
    }

    @Test
    void findOrderById() {
    }
}