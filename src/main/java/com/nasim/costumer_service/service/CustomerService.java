package com.nasim.costumer_service.service;

import com.nasim.costumer_service.dto.CustomerDto;
import com.nasim.costumer_service.dto.GenreUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerService {
    CustomerDto findCustomerById(Integer id);

    void updateCustomerGenre(Integer id, GenreUpdateRequest request);
}
