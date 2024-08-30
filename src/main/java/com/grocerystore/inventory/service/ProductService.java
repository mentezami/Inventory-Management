package com.grocerystore.inventory.service;

import com.grocerystore.inventory.execption.ResourceNotFoundException;
import com.grocerystore.inventory.model.Product;
import com.grocerystore.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + id + " not found."));
    }

    @Cacheable(value = "products", key = "#upc")
    public Product getProductByUpc(Long upc) {
        return productRepository.findByUpc(upc)
                .orElseThrow(() -> new ResourceNotFoundException("Product with UPC Number: " + upc + " not found"));
    }

    @Transactional
    @CachePut(value = "products", key = "#product.id")
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            log.error("Failed to update product as ID is null");
            throw new IllegalArgumentException("Product ID must be included and not be null.");
        }
        Product existingProduct = getProductById(product.getId());

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setInventoryCount(product.getInventoryCount());

        log.info("Updated product with ID: {}", product.getId());
        return productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            log.error("Failed to delete product with id {}", id);
            throw new ResourceNotFoundException("Product with ID: " + id + " not found.");
        }
        log.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
    }
}