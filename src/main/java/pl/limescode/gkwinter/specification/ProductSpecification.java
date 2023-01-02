package pl.limescode.gkwinter.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.limescode.gkwinter.model.Product;

public class ProductSpecification {

    public static Specification<Product> priceGreaterThanOrEqualTo(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualThan(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> nameLike(String namePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namePart));
    }

}
