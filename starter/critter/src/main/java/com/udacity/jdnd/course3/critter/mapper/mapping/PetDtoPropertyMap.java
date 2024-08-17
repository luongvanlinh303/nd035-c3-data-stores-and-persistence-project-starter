package com.udacity.jdnd.course3.critter.mapper.mapping;

import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.mapper.converter.CustomerIdConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.StringPetTypeConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetDtoPropertyMap extends PropertyMap<PetDTO, Pet> {
  private final CustomerIdConverter customerIdConverter;
  private final StringPetTypeConverter stringPetTypeConverter;

  @Override
  protected void configure() {
    using(customerIdConverter).map(source.getOwnerId(), destination.getOwner());
    using(stringPetTypeConverter).map(source.getType(), destination.getType());
  }
}
