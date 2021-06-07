package be.vdab.toyshaydar.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class OrderDetailTest {
    private OrderDetail orderDetail;
    private Product product;
    private OrderDetail orderDetail2;
    private Product product2;

    @BeforeEach
    void beforeEach() {
        product = new Product("test", "test", "test", 1000, 500, BigDecimal.TEN);
        orderDetail = new OrderDetail(product, 200, BigDecimal.TEN);
        product2 = new Product("test", "test", "test", 1000, 800, BigDecimal.TEN);
        orderDetail2 = new OrderDetail(product2, 300, BigDecimal.TEN);
    }

    @Test
    void isShippableWithCorrectQuantity() {
        assertThat(orderDetail.isShippable()).isTrue();
    }

    @Test
    void isShippableWithWrongQuantity() {
        assertThat(orderDetail2.isShippable()).isFalse();
    }

}