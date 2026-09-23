package com.nasim.costumer_service.service.impl;

import com.nasim.costumer_service.client.MovieClient;
import com.nasim.costumer_service.dto.CustomerDto;
import com.nasim.costumer_service.dto.GenreUpdateRequest;
import com.nasim.costumer_service.exception.type.CustomerNotFoundException;
import com.nasim.costumer_service.mapper.EntityDtoMapper;
import com.nasim.costumer_service.repository.CustomerRepository;
import com.nasim.costumer_service.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MovieClient movieClient;
    public CustomerServiceImpl(CustomerRepository customerRepository, MovieClient movieClient) {
        this.customerRepository = customerRepository;
        this.movieClient = movieClient;
    }
    @Override
    @Transactional(readOnly = true)
    public CustomerDto findCustomerById(Integer id){
        var customer=customerRepository.findById(id).orElseThrow(
                ()-> new CustomerNotFoundException(id)
        );
        var movies=movieClient.getAllMovieByGenre(customer.getFavoriteGenre());
       return EntityDtoMapper.mapToDto(customer,movies);

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomerGenre(Integer id, GenreUpdateRequest request){
        var customer=customerRepository.findById(id).orElseThrow(
                ()-> new CustomerNotFoundException(id)
        );
        customer.setFavoriteGenre(request.favoriteGenre());
        customerRepository.save(customer);

    }

}
