package com.nasim.costumer_service.contoller;

import com.nasim.costumer_service.dto.CustomerDto;
import com.nasim.costumer_service.dto.GenreUpdateRequest;
import com.nasim.costumer_service.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getAllCustomers(@PathVariable(name = "id") Integer id){
        var customer=customerService.findCustomerById(id);
        return ResponseEntity.ok(customer);
    }
    @PatchMapping("/{id}/genre")
    public ResponseEntity<Void> updateCustomersGenre(@PathVariable(name = "id") Integer id,@RequestBody GenreUpdateRequest request){
         customerService.updateCustomerGenre(id,request);
        return ResponseEntity.noContent().build();
    }
}
