package be.vdab.toyshaydar.services;

import be.vdab.toyshaydar.domain.*;
import be.vdab.toyshaydar.exceptions.OrderNietGevondenException;
import be.vdab.toyshaydar.exceptions.UnsufficientStockException;
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
    private DefaultOrderService service;
    @Mock
    private OrderRepository repository;
    private Customer customer;
    private Product product;
    private Product product2;
    private OrderDetail orderDetail;
    private OrderDetail orderDetail2;
    private Order order;
    private Order order2;

    @BeforeEach
    void beforeEach() {
        service = new DefaultOrderService(repository);
        product = new Product("test", "test", "test", 10, 5, BigDecimal.TEN);
        product2 = new Product("test", "test", "test", 5, 4, BigDecimal.TEN);
        orderDetail = new OrderDetail(product, 4, BigDecimal.TEN);
        orderDetail2 = new OrderDetail(product2, 6, BigDecimal.TEN);
        customer = new Customer("test", new Adress("test", "test", "test", "test"), new Country("test"));
        order = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", OrderStatus.PROCESSING, customer);
        order2 = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", OrderStatus.PROCESSING, customer);
        order.addOrderDetail(orderDetail);
        order2.addOrderDetail(orderDetail2);
    }

    @Test
    void setAsShippedForExistingAndSufficientOrder() {
        when(repository.findOrderById(1)).thenReturn(Optional.of(order));
        service.setAsShippped(1);
        assertThat(product.getInStock()).isEqualTo(6);
        assertThat(product.getInOrder()).isEqualTo(1);
        assertThat(order.getShipped()).isInstanceOf(LocalDate.class);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);
        verify(repository).findOrderById(1);
    }

    @Test
    void setAsShippedForNotExistingOrder() {
        assertThatExceptionOfType(OrderNietGevondenException.class).isThrownBy(
                () -> service.setAsShippped(-1));
        verify(repository).findOrderById(-1);
    }

    @Test
    void setAsShippedForInsufficientStockOrder() {
        when(repository.findOrderById(2)).thenReturn(Optional.of(order2));
        assertThatExceptionOfType(UnsufficientStockException.class).isThrownBy(
                () -> service.setAsShippped(2));
        verify(repository).findOrderById(2);
    }

}