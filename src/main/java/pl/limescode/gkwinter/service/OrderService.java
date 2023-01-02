package pl.limescode.gkwinter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.limescode.gkwinter.cache.Cart;
import pl.limescode.gkwinter.dto.OrderDto;
import pl.limescode.gkwinter.model.Order;
import pl.limescode.gkwinter.validator.OrderValidator;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final Cart cart;
    private final OrderValidator orderValidator;

    public Order findOrderByProductId(Long productId) {
        return cart.findOrderByProductId(productId);
    }

    public List<Order> findAll() {
        return cart.findAll();
    }

    public Order addOrder(OrderDto dto) {
        orderValidator.validate(dto);
        Order order = Order.builder()
                .productId(dto.getProductId())
                .name(dto.getName())
                .price(dto.getPrice())
                .total(dto.getPrice())
                .amount(1)
                .added(Instant.now())
                .build();
        return cart.save(order);
    }

    public void addItem(Long productId) {
        cart.addItem(productId);
    }

    public void removeItem(Long productId) {
        cart.removeItem(productId);
    }

    public void deleteOrderByProductId(Long productId) {
        cart.deleteByProductId(productId);
    }
}
