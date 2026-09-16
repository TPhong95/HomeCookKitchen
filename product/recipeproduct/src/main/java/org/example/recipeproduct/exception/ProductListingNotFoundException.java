package org.example.recipeproduct.exception;

public class ProductListingNotFoundException extends RuntimeException {
    public ProductListingNotFoundException(String message) {
        super(message);
    }
}
