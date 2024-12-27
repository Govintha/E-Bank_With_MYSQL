package com.easybank.accounts.exception;

import com.easybank.accounts.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerAlreadExitException.class)
     public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistExption(CustomerAlreadExitException customerAlreadExitException,
                                                                               WebRequest webRequest){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(

                    webRequest.getDescription(false),
                    HttpStatus.BAD_REQUEST,
                    customerAlreadExitException.getMessage(),
                    LocalDateTime.now()
            );

            return  new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);

     }

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistExption(ResourseNotFoundException resourseNotFoundException,
                                                                              WebRequest webRequest){

        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(

                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                resourseNotFoundException.getMessage(),
                LocalDateTime.now()
        );

        return  new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistExption( Exception exception ,
                                                                              WebRequest webRequest){

        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return  new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
    }


     @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

         Map<String, String> validationErrors = new HashMap<>();
         List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors(); // collect all field validation errors

         // Iterate and get the all errors
         validationErrorList.forEach((error) -> {
             String fieldName = ((FieldError) error).getField();
             String validationMsg = error.getDefaultMessage();
             validationErrors.put(fieldName, validationMsg);
         });
         return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
