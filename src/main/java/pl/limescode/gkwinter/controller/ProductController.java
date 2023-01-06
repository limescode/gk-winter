package pl.limescode.gkwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.limescode.gkwinter.converter.ProductConverter;
import pl.limescode.gkwinter.dto.ProductCreateDto;
import pl.limescode.gkwinter.dto.ProductDto;
import pl.limescode.gkwinter.model.Product;
import pl.limescode.gkwinter.service.ProductService;
import pl.limescode.gkwinter.validator.ProductValidator;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter converter;
    private final ProductValidator productValidator;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Long id) {
        return converter.entityToDto(productService.findProductById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1) {
            page = 1;
        }
        var result = productService.find(minPrice, maxPrice, namePart, page)
                .map(product -> converter.entityToDto(product));
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductCreateDto productCreateDto) {
        productValidator.validate(productCreateDto);
        return productService.createProduct(productCreateDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = converter.dtoToEntity(productDto);
        return converter.entityToDto(productService.saveProduct(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
