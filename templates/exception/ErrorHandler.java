package org.example.purchase.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    //========== 400 BAD REQUEST ==========

    @ExceptionHandler({
                //Eksempel på en class bruker denne exception handleren
                ExampleNotFoundException.class
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

    //Example
    /*
     public Chef getChefById(Long id) {
        return chefRepo.findById(id)
                .orElseThrow(() -> new ChefNotFoundException(
                        "Chef with id: " + id + " not found", #The message
                        Map.of("entityType", "Chef", "entityId", id))); #The fields
    }
     */

/*
    //========== 404 NOT FOUND ==========

    @ExceptionHandler({
    })
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
     */
/*

    //========== 409 CONFLICT ==========

    @ExceptionHandler({
    })
     ResponseEntity<ProblemDetail> handleConflictException(ConflictException e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 409);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

        log.error("409 Conflict error: {}",e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problem.setTitle("409 Conflict error");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    //========== 500 INTERNAL SERVER ERROR ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e, HttpServletRequest request) {
        Map<String, Object> fields = new LinkedHashMap<>(e.getFields());

        fields.put("status", 500);
        fields.put("path", request.getRequestURI());
        fields.put("exception", e.getClass().getSimpleName());

         log.error("500 Unexpected error: {}", e.getMessage(), entries(fields));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problem.setTitle("500 An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

 */
}