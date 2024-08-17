package com.udacity.jdnd.course3.critter.mapper.mapping;

import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.mapper.converter.AbstractEntitiesConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPropertyMap extends PropertyMap<Customer, CustomerDTO> {
  private final AbstractEntitiesConverter<Long> abstractEntitiesConverter;

  @Override
  protected void configure() {
    using(abstractEntitiesConverter).map(source.getPets(), destination.getPetIds());
  }
}
