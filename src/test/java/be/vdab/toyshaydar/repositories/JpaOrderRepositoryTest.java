package be.vdab.toyshaydar.repositories;

import be.vdab.toyshaydar.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@Import(JpaOrderRepository.class)
@Sql({"/insertProductLine.sql","/insertCountry.sql","/insertCustomer.sql","/insertProduct.sql","/insertOrder.sql","/insertOrderDetail.sql"})
class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String ORDERS = "orders";

    private final OrderRepository orderRepository;

    JpaOrderRepositoryTest(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private long idVanTestOrder() {
       return jdbcTemplate.queryForObject("select id from orders where comments='test' and ordered='2021-01-01'", Long.class);
    }


    @Test
    void findOrderById() {
        var order = orderRepository.findOrderById(idVanTestOrder()).get();
        assertThat(order.getOrdered()).isEqualTo("2021-01-01");
        assertThat(order.getComments()).isEqualTo("test");
        assertThat(order.getStatus()).isEqualTo(OrderStatus.DISPUTED);
    }


    @Test
    void findAllOrdersExceptCancelledAndShipped() {
        var orders = orderRepository.findAllOrdersExceptCancelledAndShipped();
        assertThat(orders).hasSize(countRowsInTableWhere(ORDERS,
                "status not in ('CANCELLED','SHIPPED')"))
                .extracting(Order::getId).isSorted();
        assertThat(orders).extracting(order -> order.getCustomer().getName());
    }

    @Test
    void orderLazyLoading() {

    }


}