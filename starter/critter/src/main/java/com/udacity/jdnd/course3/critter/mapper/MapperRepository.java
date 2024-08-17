package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.mapper.converter.AbstractEntitiesConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.CustomerIdConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.CustomerIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.EmployeeIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.PetIdsConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.StringPetTypeConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.StringTimeConverter;
import com.udacity.jdnd.course3.critter.mapper.converter.TimeStringConverter;
import com.udacity.jdnd.course3.critter.mapper.mapping.CustomerDtoPropertyMap;
import com.udacity.jdnd.course3.critter.mapper.mapping.CustomerPropertyMap;
import com.udacity.jdnd.course3.critter.mapper.mapping.PetDtoPropertyMap;
import com.udacity.jdnd.course3.critter.mapper.mapping.PetPropertyMap;
import com.udacity.jdnd.course3.critter.mapper.mapping.ScheduleDtoPropertyMap;
import com.udacity.jdnd.course3.critter.mapper.mapping.SchedulePropertyMap;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperRepository {
  @Bean
  public List<Converter<?, ?>> converters(
      AbstractEntitiesConverter<Long> abstractEntitiesConverter,
      CustomerIdConverter customerIdConverter,
      CustomerIdsConverter customerIdsConverter,
      EmployeeIdsConverter employeeIdsConverter,
      PetIdsConverter petIdsConverter,
      StringPetTypeConverter stringPetTypeConverter,
      StringTimeConverter stringTimeConverter,
      TimeStringConverter timeStringConverter
  ) {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(abstractEntitiesConverter);
    converters.add(customerIdConverter);
    converters.add(customerIdsConverter);
    converters.add(employeeIdsConverter);
    converters.add(petIdsConverter);
    converters.add(stringPetTypeConverter);
    converters.add(stringTimeConverter);
    converters.add(timeStringConverter);

    return converters;
  }

  @Bean
  public List<PropertyMap<?, ?>> mappings(
      CustomerPropertyMap customerPropertyMap,
      CustomerDtoPropertyMap customerDtoPropertyMap,
      PetPropertyMap petPropertyMap,
      PetDtoPropertyMap petDtoPropertyMap,
      SchedulePropertyMap schedulePropertyMap,
      ScheduleDtoPropertyMap scheduleDtoPropertyMap
  ) {
    List<PropertyMap<?, ?>> mappings = new ArrayList<>();
    mappings.add(customerPropertyMap);
    mappings.add(customerDtoPropertyMap);
    mappings.add(petPropertyMap);
    mappings.add(petDtoPropertyMap);
    mappings.add(schedulePropertyMap);
    mappings.add(scheduleDtoPropertyMap);

    return mappings;
  }
}
