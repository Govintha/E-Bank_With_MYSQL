package com.easybank.cards.exception;

import com.easybank.cards.dto.ErrorResponseDTO;
import org.springframework.http.*;
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


       @Override
       protected ResponseEntity<Object> handleMethodArgumentNotValid(
               MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

              Map<String ,String> validationMessage=new HashMap<>();


              List<ObjectError> validationErros=ex.getBindingResult().getAllErrors();

              validationErros.forEach((error)-> {
                             String filedName = ((FieldError) error).getField();
                             String message=error.getDefaultMessage();
                             validationMessage.put(filedName,message);
                      }

                      );

              return new ResponseEntity<>(validationErros, HttpStatus.BAD_REQUEST);
       }

       @ExceptionHandler(CardAlreadyExistException.class)
       public ResponseEntity<ErrorResponseDTO> cardArleadyExistException(CardAlreadyExistException cardAlreadyExistException, WebRequest webRequest){

            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(

                    webRequest.getDescription(false),
                    HttpStatus.BAD_REQUEST,
                    cardAlreadyExistException.getMessage(),
                    LocalDateTime.now()
            );

            return  new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);
       }

       @ExceptionHandler(ResourseNotFoundException.class)
       public ResponseEntity<ErrorResponseDTO> responseDTOResponseEntity(ResourseNotFoundException ex,WebRequest webRequest){

           ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                   webRequest.getDescription(false),
                   HttpStatus.BAD_REQUEST,
                   ex.getMessage(),
                   LocalDateTime.now()
           );

           return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);

       }

       @ExceptionHandler(Exception.class)
       public ResponseEntity<ErrorResponseDTO> otherException(Exception ex,WebRequest webRequest){

           ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                   webRequest.getDescription(false),
                   HttpStatus.BAD_REQUEST,
                   ex.getMessage(),
                   LocalDateTime.now()
           );

           return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);
       }
}
