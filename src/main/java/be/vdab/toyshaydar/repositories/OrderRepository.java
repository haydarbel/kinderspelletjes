package be.vdab.toyshaydar.repositories;

import be.vdab.toyshaydar.domain.Customer;
import be.vdab.toyshaydar.domain.Order;
import be.vdab.toyshaydar.domain.Product;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findOrderById(long id);
    List<Order> findAllOrdersExceptCancelledAndShipped();

}
