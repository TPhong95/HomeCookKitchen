package user.chefuser.chef.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import user.chefuser.chef.exception.DuplicateException.DuplicateEmailException;
import user.chefuser.chef.exception.NotFoundException.AddressNotFoundException;
import user.chefuser.chef.exception.NotFoundException.ChefNotFoundException;
import user.chefuser.chef.exception.NotFoundException.HomeVibeNotFoundException;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.chefuser.chef.exception.NotFoundException.NotFoundException;
import user.chefuser.chef.exception.internalServerError.InternalServerError;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.entries;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    //========== 404 NOT FOUND ==========

    @ExceptionHandler({
            ChefNotFoundException.class,
            AddressNotFoundException.class,
            HomeVibeNotFoundException.class
    })
    public ProblemDetail handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 404);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("404 Not Found error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("404 Not found");
        fields.forEach(problem::setProperty);
        return problem;
    }

    @ExceptionHandler({
            BadRequestException.class
    })
    public ProblemDetail handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 400);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("400 Bad request error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setTitle("400 Bad request");
        fields.forEach(problem::setProperty);
        return problem;
    }

    // Internal Server Error //
    @ExceptionHandler({
            InternalServerError.class
    })
    public ProblemDetail handleInternalServerError(InternalServerError e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());
        fields.put("status", 500);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());
        log.error("500 Internal server error: {}", e.getMessage(), entries(fields));
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problem.setTitle("500 Internal server error");
        fields.forEach(problem::setProperty);
        return problem;
    }
    //========== 409 CONFLICT ==========

    @ExceptionHandler({
            DuplicateEmailException.class
    })
    public ProblemDetail handleDuplicateEmailException(DuplicateEmailException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 409);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("409 Conflict error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problem.setTitle("409 Conflict");
        fields.forEach(problem::setProperty);
        return problem;
    }
}

