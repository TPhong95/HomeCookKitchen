package org.school.exam.productlisting.exception.notfound;

public class ProductListingNotFoundException extends RuntimeException {
    public ProductListingNotFoundException(String message) {
        super(message);
    }
}
