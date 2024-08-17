package com.udacity.jdnd.course3.critter.mapper.mapping;

import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Event;
import com.udacity.jdnd.course3.critter.mapper.converter.CustomerIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.EmployeeIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.PetIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.StringTimeConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleDtoPropertyMap extends PropertyMap<ScheduleDTO, Event> {
  private final StringTimeConverter stringTimeConverter;
  private final CustomerIdsConverter customerIdsConverter;
  private final EmployeeIdsConverter employeeIdsConverter;
  private final PetIdsConverter petIdsConverter;

  @Override
  protected void configure() {
    using(stringTimeConverter).map(source.getStart(), destination.getStart());
    using(stringTimeConverter).map(source.getEnd(), destination.getEnd());
    using(customerIdsConverter).map(source.getCustomerIds(), destination.getCustomers());
    using(employeeIdsConverter).map(source.getEmployeeIds(), destination.getEmployees());
    using(petIdsConverter).map(source.getPetIds(), destination.getPets());
  }
}
