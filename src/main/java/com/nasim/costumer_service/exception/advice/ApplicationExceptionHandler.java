package com.nasim.costumer_service.exception.advice;

import com.nasim.costumer_service.exception.type.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handel(CustomerNotFoundException e){
        var problemdetail= ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                 e.getMessage()
                );
        problemdetail.setTitle("Customer not found");
        return  problemdetail;
    }
}
