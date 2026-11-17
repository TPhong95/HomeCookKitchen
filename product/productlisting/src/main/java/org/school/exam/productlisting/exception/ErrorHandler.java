package org.school.exam.productlisting.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.school.exam.productlisting.exception.BadRequestException.BadRequestException;
import org.school.exam.productlisting.exception.ConflictException.ConflictException;
import org.school.exam.productlisting.exception.NotFoundException.NotFoundException;
import org.school.exam.productlisting.exception.NotFoundException.ProductListingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.entries;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    //====== 400 Bad Request ======
    @ExceptionHandler({BadRequestException.class})
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

    //====== 404 Not Found ======
    @ExceptionHandler({ProductListingNotFoundException.class})
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
    //====== 409 Conflict ======
    @ExceptionHandler({ConflictException.class})
    public ProblemDetail handleConflictException(ConflictException e, HttpServletRequest request) {
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

    //====== 500 Internal server error ======
}
