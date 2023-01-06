package pl.limescode.gkwinter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class Order {
    private Long productId;
    private String name;
    private Integer price;
    private Integer total;
    private Integer amount;
    private Instant added;
}
