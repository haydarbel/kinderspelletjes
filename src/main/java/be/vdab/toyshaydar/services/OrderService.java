package be.vdab.toyshaydar.services;

import be.vdab.toyshaydar.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void setAsShippped(long id);
    List<Order> findOrdersToBeShip();
    Optional<Order> findOrderById(long id);

}
