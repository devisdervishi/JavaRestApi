package com.devis.java.spring.demo.errors;

import com.devis.java.spring.demo.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {


   @org.springframework.web.bind.annotation.ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorMessage> personNotFound(PersonNotFoundException exception , WebRequest request){

        ErrorMessage message=new ErrorMessage(((ServletWebRequest)request).getRequest().getRequestURL().toString(),
                HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
