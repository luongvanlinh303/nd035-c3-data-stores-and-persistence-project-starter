package com.udacity.jdnd.course3.critter.mapper.converter;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerIdsConverter extends AbstractConverter<List<Long>, Set<Customer>> {
  private final CustomerRepository repository;

  @Override
  protected Set<Customer> convert(List<Long> customerIds) {
    return repository.findAllByIds(customerIds);
  }
}
