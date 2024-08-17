package com.udacity.jdnd.course3.critter.mapper.converter;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.NotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerIdConverter extends AbstractConverter<Long, Customer> {
  private final CustomerRepository repository;

  @Override
  protected Customer convert(Long customerId) {
    return repository.findById(customerId)
        .orElseThrow(() -> new NotFoundException("Customer does not exist"));
  }
}
