package user.customer.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends RuntimeException {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);


    public ErrorHandler(String message) {
        super(message);
    }
}
