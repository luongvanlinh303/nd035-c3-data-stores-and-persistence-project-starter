package com.udacity.jdnd.course3.critter.mapper.converter;

import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetIdsConverter extends AbstractConverter<List<Long>, Set<Pet>> {
  private final PetRepository repository;

  @Override
  protected Set<Pet> convert(List<Long> petIds) {
    return repository.findAllByIds(petIds);
  }
}
