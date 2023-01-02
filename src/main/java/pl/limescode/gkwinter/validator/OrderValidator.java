package pl.limescode.gkwinter.validator;

import org.springframework.stereotype.Component;
import pl.limescode.gkwinter.dto.OrderDto;
import pl.limescode.gkwinter.exception.ValidationException;

@Component
public class OrderValidator {

    public void validate(OrderDto dto) {
        if (dto.getProductId() == null) {
            throw new ValidationException("Product id can not be empty");
        }
        if (dto.getName().isBlank()) {
            throw new ValidationException("Name of the product can't be blank");
        }
        if (dto.getPrice() < 0) {
            throw new ValidationException("Price can not be lower than 0");
        }
    }


}
