package pl.limescode.gkwinter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long productId;
    private String name;
    private Integer price;
}
