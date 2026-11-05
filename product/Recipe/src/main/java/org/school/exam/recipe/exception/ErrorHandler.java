package org.school.exam.recipe.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.school.exam.recipe.exception.BadRequestException.BadRequestException;
import org.school.exam.recipe.exception.NotFoundException.NotFoundException;
import org.school.exam.recipe.exception.NotFoundException.RecipeNotFoundException;
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
    @ExceptionHandler({
            BadRequestException.class
    }
    )
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 400);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("400 Bad Request error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setTitle("400 Bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }


    //====== 404 Not Found ======
    @ExceptionHandler({RecipeNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 404);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("404 Not Found error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("404 Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    //====== 409 Conflict ======

    //====== 500 Internal server error ======
}
