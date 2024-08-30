package com.grocerystore.inventory.validation;

import com.grocerystore.inventory.model.Product;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductValidator implements ConstraintValidator<ValidProduct, Product> {

    @Override
    public void initialize(ValidProduct constraintAnnotation) {
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        boolean valid = true;

        if (product.getPrice() <= 0) {
            context.buildConstraintViolationWithTemplate("Price must br positive")
                    .addPropertyNode("price")
                    .addConstraintViolation();
            valid = false;
        }

        if (product.getInventoryCount() < 0) {
            context.buildConstraintViolationWithTemplate("Inventory count must be either zero or positive")
                    .addPropertyNode("inventoryCount")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}