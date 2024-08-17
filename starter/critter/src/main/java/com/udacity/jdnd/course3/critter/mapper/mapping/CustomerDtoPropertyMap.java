package com.udacity.jdnd.course3.critter.mapper.mapping;

import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.mapper.converter.PetIdsConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDtoPropertyMap extends PropertyMap<CustomerDTO, Customer> {
  private final PetIdsConverter petIdsConverter;

  @Override
  protected void configure() {
    using(petIdsConverter).map(source.getPetIds(), destination.getPets());
  }
}
