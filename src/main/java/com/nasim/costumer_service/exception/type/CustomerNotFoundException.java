package com.nasim.costumer_service.exception.type;


public class CustomerNotFoundException extends RuntimeException {
    private static final String MESSAGE="the customer with id=%d not found";

    public CustomerNotFoundException(Integer id) {
      super(MESSAGE.formatted(id));
    }
}
