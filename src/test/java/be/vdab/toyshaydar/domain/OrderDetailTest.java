package be.vdab.toyshaydar.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDetailTest {
    private OrderDetail orderDetail1;
    private Product product1;
    private OrderDetail orderDetail2;
    private Product product2;

    @BeforeEach
    void beforeEach() {
        product1 = new Product("test", "test", "test", 1000, 500, BigDecimal.TEN);
        product2 = new Product("test", "test", "test", 200, 800, BigDecimal.TEN);
        orderDetail1 = new OrderDetail(product1, 200, BigDecimal.TEN);
        orderDetail2 = new OrderDetail(product2, 300, BigDecimal.TEN);
    }

    @Test
    void canItBeShipped() {
        assertThat(orderDetail1.canItBeShipped()).isTrue();
        assertThat(orderDetail2.canItBeShipped()).isFalse();
    }
    @Test
    void isShippableWithCorrectAndWrongQuantity() {
        assertThat(orderDetail1.reduceInorderAndInStockWithOrdered()).isTrue();
        assertThat(orderDetail2.reduceInorderAndInStockWithOrdered()).isFalse();
    }

    @Test
    void getValue() {
        assertThat(orderDetail1.getValue()).isEqualByComparingTo("2000");
        assertThat(orderDetail2.getValue()).isEqualByComparingTo("3000");
    }
}