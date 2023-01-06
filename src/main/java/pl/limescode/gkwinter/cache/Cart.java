package pl.limescode.gkwinter.cache;

import org.springframework.stereotype.Component;
import pl.limescode.gkwinter.exception.ResourceNotFoundException;
import pl.limescode.gkwinter.model.Order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class Cart {
    private final HashMap<Long, Order> orders = new HashMap<>();

    public Order findOrderByProductId(Long id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new ResourceNotFoundException(
                    "Order with such product ID doesn't exist");
        } else return order;
    }

    public List<Order> findAll() {
        return new ArrayList<Order>(orders.values());
    }

    public Order save(Order order) {
        var existing = orders.get(order.getProductId());
        if (existing == null) {
            orders.put(order.getProductId(), order);
            return order;
        } else {
            return update(existing, true);
        }
    }

    public void addItem(Long productId) {
        Order order = findOrderByProductId(productId);
        update(order, true);
    }

    public void removeItem(Long productId) {
        Order found = findOrderByProductId(productId);
        var updated = update(found, false);
        if (updated.getAmount()<1){
            deleteByProductId(productId);
        }
    }

    public void deleteByProductId(Long productId) {
        orders.remove(productId);
    }

    private Order update(Order order, boolean isIncrementing) {
        Integer oldTotal = order.getTotal();
        Integer oldAmount = order.getAmount();
        if (isIncrementing) {
            order.setTotal(oldTotal + order.getPrice());
            order.setAmount(++oldAmount);
        } else {
            order.setTotal(oldTotal - order.getPrice());
            order.setAmount(--oldAmount);
        }
        order.setAdded(Instant.now());
        return order;
    }

}
