package be.vdab.toyshaydar.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    private OrderDetail orderDetail;
    private Product product;
    private OrderDetail orderDetail2;
    private Product product2;
    private Order order;
    private Customer customer;

    @BeforeEach
    void beforeEach() {
        product = new Product("test", "test", "test", 1000, 500, BigDecimal.TEN);
        product2 = new Product("test", "test", "test", 500, 800, BigDecimal.TEN);
        orderDetail = new OrderDetail(product, 200, BigDecimal.TEN);
        orderDetail2 = new OrderDetail(product2, 300, BigDecimal.TEN);
        customer = new Customer("test", new Adress("test","test","test","test"), new Country("test"));
        order = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", OrderStatus.PROCESSING, customer);
        order.addOrderDetail(orderDetail);
        order.addOrderDetail(orderDetail2);
    }

    @Test
    void setOrderAsShipped() {
        order.setOrderAsShipped();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPED);
        assertThat(product.getInOrder()).isEqualTo(300);
        assertThat(product.getInStock()).isEqualTo(800);
    }
}