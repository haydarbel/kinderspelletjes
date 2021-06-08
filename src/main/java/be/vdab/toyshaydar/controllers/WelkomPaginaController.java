package be.vdab.toyshaydar.controllers;

import be.vdab.toyshaydar.exceptions.OrderNietGevondenException;
import be.vdab.toyshaydar.exceptions.UnsufficientStockException;
import be.vdab.toyshaydar.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
class WelkomPaginaController {
    private final OrderService orderService;

    WelkomPaginaController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ModelAndView index() {
        return new ModelAndView("index", "ordersToBeShip",
                orderService.findAllOrdersExceptCancelledAndShipped());
    }

    @PostMapping("shipped")
    public String shipped(Optional<Long[]> id, RedirectAttributes redirectAttributes) {
        List<Long> canNotBeSetShipped = new ArrayList<>();
        if (id.isPresent()) {
            var orderIdsToBeShipped = id.get();
            for (var orderId : orderIdsToBeShipped) {
                try {
                    orderService.setAsShippped(orderId);
                } catch (UnsufficientStockException e) {
                    canNotBeSetShipped.add(orderId);
                } catch (OrderNietGevondenException ignored) {

                }
            }
            if (!canNotBeSetShipped.isEmpty()) {
                redirectAttributes.addFlashAttribute("failedOrders", "Shipping failed for Order(s) " + canNotBeSetShipped.toString() + ",not enough stock!");
            }
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("noOrderSelected", "You must chose at least an order!");
        return "redirect:/";
    }

    @GetMapping("detail/{id}")
    public ModelAndView order(@PathVariable("id") @Positive long id) {
        var modelAndView = new ModelAndView("order");
        orderService.findOrderById(id)
                .ifPresent(order ->
                        modelAndView.addObject("order", order));
        return modelAndView;
    }
}
