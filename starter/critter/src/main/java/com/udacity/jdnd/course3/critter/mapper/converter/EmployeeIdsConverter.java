package com.udacity.jdnd.course3.critter.mapper.converter;

import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeIdsConverter extends AbstractConverter<List<Long>, Set<Employee>> {
  private final EmployeeRepository repository;

  @Override
  protected Set<Employee> convert(List<Long> employeeIds) {
    return repository.findAllByIds(employeeIds);
  }
}
