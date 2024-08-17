package com.udacity.jdnd.course3.critter.mapper.converter;

import com.udacity.jdnd.course3.critter.domain.contract.BaseIdEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class AbstractEntitiesConverter<TID> extends AbstractConverter<Collection<BaseIdEntity<TID>>, List<TID>> {
  @Override
  protected List<TID> convert(Collection<BaseIdEntity<TID>> baseIdEntities) {
    return baseIdEntities.stream()
        .map(BaseIdEntity::getId)
        .collect(Collectors.toList());
  }
}
