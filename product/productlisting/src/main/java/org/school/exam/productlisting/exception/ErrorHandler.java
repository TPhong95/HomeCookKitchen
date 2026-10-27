package org.school.exam.productlisting.exception;

import lombok.extern.slf4j.Slf4j;
import org.school.exam.productlisting.exception.notfound.ProductListingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    //====== 400 Bad Request ======


    //====== 404 Not Found ======
    @ExceptionHandler({ProductListingNotFoundException.class})
    public ResponseEntity<String> handleProductListingNotFoundException(ProductListingNotFoundException e) {
        log.error("404 Not Found Exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    //====== 409 Conflict ======

    //====== 500 Internal server error ======
}
