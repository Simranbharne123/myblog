package com.blogapi.exceptions;
import com.blogapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice // if any exception occurs at controller layer then that exception will be handled by this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class) //  the  only  exception  will handle is ResourceNotFoundException if we mention the class bt if we write the exception then will handle all
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                WebRequest webRequest){ //webrequest is a built in class which helps us to send back some info to postman
        ErrorDetails errorDetails= new ErrorDetails(new Date(),exception.getMessage(),
                                              webRequest.getDescription(false));
      return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
//to handle the exception in springboot we develop exclusive class and this class acts like a catch block
// for the complete controller layer any exception occurs anywhere in project
// it will automatically go to that class