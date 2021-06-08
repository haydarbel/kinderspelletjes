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
    public Optional<Order> findOrderById(long id) {
        return Optional.ofNullable(manager.find(Order.class, id));
    }



    @Override
    public List<Order> findAllOrdersExceptCancelledAndShipped() {
        return manager.createNamedQuery("Order.findAllOrdersExceptCancelledShipped", Order.class)
                .getResultList();
    }






}
