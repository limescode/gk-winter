package pl.limescode.gkwinter.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.limescode.gkwinter.dto.ProductCreateDto;
import pl.limescode.gkwinter.dto.ProductDto;
import pl.limescode.gkwinter.model.Product;
import pl.limescode.gkwinter.repository.ProductRepository;
import pl.limescode.gkwinter.specification.ProductSpecification;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String partName, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceLessThanOrEqualThan(maxPrice));
        }
        if (partName != null && !partName.isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(partName));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public Product createProduct(ProductCreateDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCreated(Instant.now());
        return productRepository.save(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new NoSuchElementException());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
