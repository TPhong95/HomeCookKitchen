package user.chefuser.chef.exception;


import lombok.extern.slf4j.Slf4j;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.notfound.AddressNotFoundException;
import user.chefuser.chef.exception.notfound.ChefNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    //========== 400 BAD REQUEST ==========

    @ExceptionHandler({
            BadRequestException.class
    }
    )
    public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
        log.error("400 Bad Request error: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //========== 404 NOT FOUND ==========

    @ExceptionHandler({
            ChefNotFoundException.class,
            AddressNotFoundException.class

    })
    public ResponseEntity<String> handleNotFoundException(RuntimeException e) {
        log.error("404 Not Found error: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
    //========== 409 CONFLICT ==========

    @ExceptionHandler({
    })
    public ResponseEntity<String> handleConflictException(RuntimeException e) {
        log.error("409 Conflict error: {}",e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    //========== 500 INTERNAL SERVER ERROR ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.error("500 Unexpected error: {}", e.getMessage(), e);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

     */
}

