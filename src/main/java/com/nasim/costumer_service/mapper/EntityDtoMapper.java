package com.nasim.costumer_service.mapper;

import com.nasim.costumer_service.dto.CustomerDto;
import com.nasim.costumer_service.dto.MovieDto;
import com.nasim.costumer_service.entity.Customer;

import java.util.List;

public class EntityDtoMapper {

    public  static CustomerDto mapToDto(Customer customer, List<MovieDto> movies){
        return new CustomerDto(customer.getId(),customer.getName().toUpperCase(),customer.getFavoriteGenre()
        , movies);
    }

}
