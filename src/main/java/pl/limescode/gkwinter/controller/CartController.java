package pl.limescode.gkwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.limescode.gkwinter.dto.OrderDto;
import pl.limescode.gkwinter.model.Order;
import pl.limescode.gkwinter.service.OrderService;
import pl.limescode.gkwinter.validator.OrderValidator;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final OrderService orderService;
    private final OrderValidator orderValidator;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderByProductId(@PathVariable Long productId) {
        return orderService.findOrderByProductId(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@RequestBody OrderDto orderDto) {
        orderValidator.validate(orderDto);
        return orderService.addOrder(orderDto);
    }

    @PutMapping("/incr/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addItem(@PathVariable Long productId) {
        orderService.addItem(productId);
    }

    @PutMapping("/decr/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeItem(@PathVariable Long productId) {
        orderService.removeItem(productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrderByProductId(@PathVariable Long productId) {
        orderService.deleteOrderByProductId(productId);
    }

}
