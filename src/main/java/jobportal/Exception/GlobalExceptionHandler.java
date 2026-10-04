package jobportal.Exception;

import jobportal.DTO.ExceptionErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionErrorDTO> HandlingException(MethodArgumentNotValidException ex){
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error->{
        errors.put(error.getField(),error.getDefaultMessage() );
    });
    ExceptionErrorDTO response = new ExceptionErrorDTO(
            400,
            "Validation failed",
            errors
        );

    return new ResponseEntity<>(
            response,
            HttpStatus.BAD_REQUEST
    );
}

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionErrorDTO> HandlingException(ResourceNotFoundException ex){
    String message= ex.getMessage();
    ExceptionErrorDTO response = new ExceptionErrorDTO(
            404,
            message,
            null
    );
    return new ResponseEntity<>(
            response,
            HttpStatus.NOT_FOUND
    );
}
}
