package com.udacity.jdnd.course3.critter.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {
  @Bean
  public ModelMapper modelMapper(List<Converter<?, ?>> converters, List<PropertyMap<?, ?>> mappings) {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    converters.forEach(mapper::addConverter);
    mappings.forEach(mapper::addMappings);

    return mapper;
  }
}
