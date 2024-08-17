package com.udacity.jdnd.course3.critter.mapper.mapping;

import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Event;
import com.udacity.jdnd.course3.critter.mapper.converter.AbstractEntitiesConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.TimeStringConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulePropertyMap extends PropertyMap<Event, ScheduleDTO> {
  private final TimeStringConverter timeStringConverter;
  private final AbstractEntitiesConverter<Long> abstractEntitiesConverter;

  @Override
  protected void configure() {
    using(timeStringConverter).map(source.getStart(), destination.getStart());
    using(timeStringConverter).map(source.getEnd(), destination.getEnd());
    using(abstractEntitiesConverter).map(source.getCustomers(), destination.getCustomerIds());
    using(abstractEntitiesConverter).map(source.getEmployees(), destination.getEmployeeIds());
    using(abstractEntitiesConverter).map(source.getPets(), destination.getPetIds());
  }
}
