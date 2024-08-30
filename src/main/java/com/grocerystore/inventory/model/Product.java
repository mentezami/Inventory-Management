package com.grocerystore.inventory.model;

import com.grocerystore.inventory.validation.ValidProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@ValidProduct
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Min(value = 10000000, message = "UPC number must be at least 8 digits")
    private Long upc;

    @Column(nullable = false)
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Column(nullable = false)
    @Positive(message = "Price must be positive")
    private double price;

    @Column(name = "inventory_count", nullable = false)
    @PositiveOrZero(message = "Inventory count must be either zero or positive")
    private int inventoryCount;
}