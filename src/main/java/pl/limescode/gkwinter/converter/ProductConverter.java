package pl.limescode.gkwinter.converter;

import org.springframework.stereotype.Component;
import pl.limescode.gkwinter.dto.ProductDto;
import pl.limescode.gkwinter.model.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        return ProductDto
                .builder().id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public Product dtoToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }
}
