package be.vdab.toyshaydar.repositories;

import be.vdab.toyshaydar.domain.Customer;
import be.vdab.toyshaydar.domain.Order;
import be.vdab.toyshaydar.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@Repository
public class JpaOrderRepository implements OrderRepository {
    private final EntityManager manager;

    public JpaOrderRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Customer> findCustomerById(long id) {
        return Optional.ofNullable(manager.find(Customer.class, id));
    }

    @Override
    public Optional<Product> findProductById(long id) {
        return Optional.ofNullable(manager.find(Product.class, id));
    }

    @Override
    public Optional<Order> findOrderById(long id) {
        return Optional.ofNullable(manager.find(Order.class, id));
    }

    @Override
    public List<Order> findCencelledOrders() {
        return manager.createNamedQuery("Order.findCancelledOrders", Order.class)
                .getResultList();
    }

    @Override
    public List<Order> findAllOrdersExceptCancelledShipped() {
        return manager.createNamedQuery("Order.findAllOrdersExceptCancelledShipped", Order.class)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersByIds(List<Long> ids) {
        return manager.createNamedQuery("Order.findOrdersByIds", Order.class)
                .setParameter("ids", ids)
                .getResultList();
    }


    @Override
    public List<Order> findAllOrders() {
        return manager.createNamedQuery("Order.findAllOrders", Order.class)
                .getResultList();
    }

}
