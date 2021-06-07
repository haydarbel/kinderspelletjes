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
    private static final String CUSTOMERS = "customers";
    private static final String ORDERS = "orders";

    private final OrderRepository orderRepository;

    JpaOrderRepositoryTest(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private long idVanTestCustomer() {
        return jdbcTemplate.queryForObject("select id from customers where name='test'", Long.class);
    }
    private long idVanTestProduct() {
        return jdbcTemplate.queryForObject("select id from products where name='test1'", Long.class);
    }

    private long idVanTestOrder() {
       return jdbcTemplate.queryForObject("select id from orders where comments='test'", Long.class);
    }

    @Test
    void findCustomerById() {
        assertThat(orderRepository.findCustomerById(idVanTestCustomer())
                .get().getName()).isEqualTo("test");
        assertThat(orderRepository.findCustomerById(idVanTestCustomer())
                .get().getCountry().getName()).isEqualTo("test");

    }
    @Test
    void findProductById() {
        assertThat(orderRepository.findProductById(idVanTestProduct())
                .get().getName()).isEqualTo("test1");
        assertThat(orderRepository.findProductById(idVanTestProduct())
                .get().getVersion()).isEqualTo(0);

    }
    @Test
    void findCustomerByOnbestandeId() {
        assertThat(orderRepository.findCustomerById(-1)).isNotPresent();
    }
    @Test
    void findProductByOnbestandeId() {
        assertThat(orderRepository.findProductById(-1)).isNotPresent();
    }

    @Test
    void findOrderById() {
        var order = orderRepository.findOrderById(idVanTestOrder()).get();
        assertThat(order.getOrderDetails()).hasSize(2);
    }

    @Test
    void findAllOrders() {
        var orders = orderRepository.findAllOrders();
        var cancelledOrders = orderRepository.findCencelledOrders();
        assertThat(orders).hasSize(countRowsInTable(ORDERS))
                .extracting(Order::getOrdered).isSorted();
        assertThat(orders).extracting(order -> order.getCustomer().getName());
        assertThat(orders).extracting(order -> order.getStatus().name());
        assertThat(cancelledOrders).extracting(Order::getStatus)
                .element(1).isEqualTo(OrderStatus.CANCELLED);
        assertThat(cancelledOrders).hasSize(countRowsInTableWhere(ORDERS,
                "status='CANCELLED'"));
    }


    @Test
    void findAllOrdersExceptCancelledShipped() {
        var orders = orderRepository.findAllOrdersExceptCancelledShipped();
        assertThat(orders).hasSize(countRowsInTableWhere(ORDERS,
                "status not in ('CANCELLED','SHIPPED')"));
    }
}