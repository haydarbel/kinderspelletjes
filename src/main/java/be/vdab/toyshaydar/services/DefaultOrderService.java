package be.vdab.toyshaydar.services;

import be.vdab.toyshaydar.domain.Order;
import be.vdab.toyshaydar.exceptions.OrderNietGevondenException;
import be.vdab.toyshaydar.exceptions.UnsufficientStockException;
import be.vdab.toyshaydar.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultOrderService implements OrderService {
    private final OrderRepository repository;

    public DefaultOrderService(OrderRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public void setAsShippped(long id) {
        if (!repository.findOrderById(id)
                .orElseThrow(OrderNietGevondenException::new)
                .setOrderAsShipped()) {
            throw new UnsufficientStockException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrdersExceptCancelledAndShipped() {
        return repository.findAllOrdersExceptCancelledAndShipped();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(long id) {
        return repository.findOrderById(id);
    }


}
