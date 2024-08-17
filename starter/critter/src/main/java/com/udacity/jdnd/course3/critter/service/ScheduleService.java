package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Event;
import com.udacity.jdnd.course3.critter.exception.AlreadyExistsException;
import com.udacity.jdnd.course3.critter.mapper.converter.StringTimeConverter;
import com.udacity.jdnd.course3.critter.repository.EventRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
  private final ModelMapper mapper;
  private final EventRepository repository;

  private void validateForCreate(ScheduleDTO scheduleDTO) {
    long count = repository.countById(scheduleDTO.getId());
    if (count == 1) {
      throw new AlreadyExistsException(String.format("Schedule ID %d already exists", scheduleDTO.getId()));
    }
    count = repository.countByNameDate(scheduleDTO.getName(), scheduleDTO.getDate());
    if (count == 1) {
      throw new AlreadyExistsException("Schedule is already created before");
    }
    LocalTime start = StringTimeConverter.parse(scheduleDTO.getStart());
    LocalTime end = StringTimeConverter.parse(scheduleDTO.getEnd());
    count = repository.countBySlotAndCustomerId(
        scheduleDTO.getDate(),
        start,
        end,
        scheduleDTO.getCustomerIds()
    );
    if (count >= 1) {
      throw new AlreadyExistsException("One or more Customer(s) has a schedule conflict");
    }
    count = repository.countBySlotAndEmployeeId(
        scheduleDTO.getDate(),
        start,
        end,
        scheduleDTO.getCustomerIds()
    );
    if (count >= 1) {
      throw new AlreadyExistsException("One or more Employee(s) has a schedule conflict");
    }
    count = repository.countBySlotAndPetId(
        scheduleDTO.getDate(),
        start,
        end,
        scheduleDTO.getCustomerIds()
    );
    if (count >= 1) {
      throw new AlreadyExistsException("One or more Pet(s) has a schedule conflict");
    }
  }

  public ScheduleDTO create(ScheduleDTO scheduleDTO) {
    validateForCreate(scheduleDTO);
    Event event = mapper.map(scheduleDTO, Event.class);
    event = repository.save(event);
    scheduleDTO.setId(event.getId());

    return scheduleDTO;
  }

  public List<ScheduleDTO> getAll() {
    return repository.findAll().stream()
        .map(event -> mapper.map(event, ScheduleDTO.class))
        .collect(Collectors.toList());
  }

  public List<ScheduleDTO> getAllByCustomerId(long customerId) {
    return repository.getAllByCustomerId(customerId).stream()
        .map(event -> mapper.map(event, ScheduleDTO.class))
        .collect(Collectors.toList());
  }

  public List<ScheduleDTO> getAllByEmployeeId(long employeeId) {
    return repository.getAllByEmployeeId(employeeId).stream()
        .map(event -> mapper.map(event, ScheduleDTO.class))
        .collect(Collectors.toList());
  }

  public List<ScheduleDTO> getAllByPetId(long petId) {
    return repository.getAllByPetId(petId).stream()
        .map(event -> mapper.map(event, ScheduleDTO.class))
        .collect(Collectors.toList());
  }
}
