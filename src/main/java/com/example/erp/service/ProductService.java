package com.example.erp.service;

import com.example.erp.ProductMapper;
import com.example.erp.dto.ProductRequest;
import com.example.erp.dto.ProductResponse;
import com.example.erp.exception.ProductNotFoundException;
import com.example.erp.model.Product;
import com.example.erp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repository.findAll(Sort.by("id").ascending())
                .stream().map(ProductMapper::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("products is not found"));
    }

    @Transactional
    public Product create(ProductRequest request) {

        String product = request.name().trim();
        if (repository.existsBySkuIgnoreCase(product)) {
            throw new IllegalArgumentException("SKU already exists: " + product);
        }
        Product newProduct = Product.builder()
                .sku(product)
                .productName(request.name().trim())
                .category(request.category().trim())
                .price(request.price())
                .quantity(request.quantity())
                .reorderLevel(request.reorderLevel())
                .build();
        return repository.save(newProduct);
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest request, Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + id)
        );
        product.setProductName(request.name().trim());
        product.setCategory(request.category().trim());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setReorderLevel(request.reorderLevel());

        repository.save(product);
        return ProductMapper.toProductResponse(product);

    }

    @Transactional
    public String deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("product is not found with id:" + id));
        repository.deleteById(product.getId());
        return "Product is deleted" + id;
    }

    @Transactional
    public ProductResponse updatePartial(ProductResponse productRequest, Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("product is not found" + id));

        Optional.ofNullable(productRequest.category()).ifPresent(product::setCategory);

       /* if(productRequest.category()!=null){
           product.setCategory(productRequest.category());
        }*/

        Optional.ofNullable(productRequest.price()).ifPresent(product::setPrice);

        Optional.ofNullable(productRequest.quantity()).ifPresent(product::setQuantity);

        Optional.ofNullable(productRequest.reorderLevel()).ifPresent(product::setReorderLevel);

        Optional.ofNullable(productRequest.sku()).ifPresent(product::setSku);

        Optional.ofNullable(productRequest.id()).ifPresent(product::setId);

        repository.save(product);
        return ProductMapper.toProductResponse(product);
    }


}
