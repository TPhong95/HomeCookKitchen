package org.school.exam.productlisting.exception.NotFoundException;

import java.util.Map;

public class ProductListingNotFoundException extends NotFoundException {
    public ProductListingNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
