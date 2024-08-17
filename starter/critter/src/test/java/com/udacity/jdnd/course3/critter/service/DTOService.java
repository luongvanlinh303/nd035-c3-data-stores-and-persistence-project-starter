package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.dto.UserDTO;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.utils.CollectionUtil;
import com.udacity.jdnd.course3.critter.utils.DateUtil;
import com.udacity.jdnd.course3.critter.utils.FakeUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

@Service
public class DTOService {
  private static final int MAX_ID_BOUND = 100;

  private long getId() {
    return ThreadLocalRandom.current().nextInt(MAX_ID_BOUND) + 1;
  }

  private void setUserData(UserDTO userDto, Long id) {
    if (id != null) userDto.setId(id);
    userDto.setName(FakeUtil.generateName().fullName());
    userDto.setPhoneNumber(FakeUtil.generatePhoneNumber());
    userDto.setEmailAddress(FakeUtil.generateEmail());
    userDto.setFullAddress(FakeUtil.generateAddress());
    userDto.setCity(FakeUtil.generateCity());
  }

  public CustomerDTO generateCustomer(Long id) {
    CustomerDTO customerDTO = new CustomerDTO();
    setUserData(customerDTO, id);
    customerDTO.setNotes(FakeUtil.service.numerify("Note ##"));
    return customerDTO;
  }

  public EmployeeDTO generateEmployee(Long id, Iterable<EmployeeSkill> skills, Iterable<DayOfWeek> daysAvailable) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    setUserData(employeeDTO, id);
    employeeDTO.setSkills(Sets.newHashSet(skills));
    employeeDTO.setDaysAvailable(Sets.newHashSet(daysAvailable));
    return employeeDTO;
  }

  public EmployeeDTO generateEmployee(Long id) {
    Set<EmployeeSkill> skills = Sets.newHashSet(CollectionUtil.pickNRandom(Arrays.asList(EmployeeSkill.values()), 2));
    Set<DayOfWeek> daysAvailable = Sets.newHashSet(CollectionUtil.pickNRandom(Arrays.asList(DayOfWeek.values()), 3));
    return generateEmployee(id, skills, daysAvailable);
  }

  public PetDTO generatePet(Long id, Long ownerId) {
    PetDTO petDTO = new PetDTO();
    if (id != null) petDTO.setId(id);
    petDTO.setName(FakeUtil.generateName().firstName() + " Pet");
    petDTO.setType("CAT");
    petDTO.setOwnerId(ownerId);
    return petDTO;
  }

  public ScheduleDTO generateSchedule(Long id, List<Long> petIds, List<Long> employeeIds, List<Long> customerIds) {
    Set<EmployeeSkill> skills = Sets.newHashSet(CollectionUtil.pickNRandom(Arrays.asList(EmployeeSkill.values()), 2));
    return generateSchedule(id, skills, petIds, employeeIds, customerIds);
  }

  public ScheduleDTO generateSchedule(Long id, Set<EmployeeSkill> skills, List<Long> petIds, List<Long> employeeIds, List<Long> customerIds) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();
    if (id != null) scheduleDTO.setId(id);
    scheduleDTO.setName(FakeUtil.service.numerify("Event ##"));
    scheduleDTO.setPetIds(petIds);
    scheduleDTO.setEmployeeIds(employeeIds);
    scheduleDTO.setCustomerIds(customerIds);
    scheduleDTO.setDate(FakeUtil.generateDate());
    scheduleDTO.setStart(FakeUtil.generateTime());
    scheduleDTO.setEnd(FakeUtil.generateTime());
    scheduleDTO.setActivities(skills);
    return scheduleDTO;
  }

  public ScheduleDTO generateSchedule(List<PetDTO> pets, List<EmployeeDTO> employees, List<CustomerDTO> customers) {

    return generateSchedule(
        null,
        pets.stream().map(PetDTO::getId).collect(Collectors.toList()),
        employees.stream().map(UserDTO::getId).collect(Collectors.toList()),
        customers.stream().map(UserDTO::getId).collect(Collectors.toList()));
  }

  public List<PetDTO> batchPets(int petCount, List<CustomerDTO> customers) {
    return LongStream.range(0, petCount)
        .mapToObj(i -> customers.stream()
            .map(customer -> generatePet(null, customer.getId()))
            .collect(Collectors.toList()))
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toList());
  }

  public List<EmployeeDTO> batchEmployees(int employeeCount) {
    return LongStream.range(0, employeeCount)
        .mapToObj(id -> generateEmployee(null))
        .collect(Collectors.toList());
  }

  public List<CustomerDTO> batchCustomers(int customerCount) {
    return LongStream.range(0, customerCount)
        .mapToObj(this::generateCustomer)
        .collect(Collectors.toList());
  }

  public List<CustomerDTO> batchCustomer(Long... ids) {
    if (ids.length == 0) {
      return Collections.singletonList(generateCustomer(getId()));
    }

    return Arrays.stream(ids).sequential()
        .map(this::generateCustomer)
        .collect(Collectors.toList());
  }

  public List<EmployeeDTO> batchEmployee(Long... ids) {
    if (ids.length == 0) {
      return Collections.singletonList(generateEmployee(getId()));
    }

    return Arrays.stream(ids).sequential()
        .map(this::generateEmployee)
        .collect(Collectors.toList());
  }

  public List<PetDTO> batchPet(Pair<Long, Long>... ids) {
    if (ids.length == 0) throw new IllegalArgumentException();

    return Arrays.stream(ids).sequential()
        .map((id) -> generatePet(id.getKey(), id.getValue()))
        .collect(Collectors.toList());
  }

  public EmployeeRequestDTO generateEmployeeRequestDTO(EmployeeDTO employeeDTO) {
    if (employeeDTO.getSkills() == null || employeeDTO.getSkills().isEmpty()) throw new IllegalArgumentException();
    if (employeeDTO.getDaysAvailable() == null || employeeDTO.getDaysAvailable().isEmpty())
      throw new IllegalArgumentException();

    int year = Calendar.getInstance().get(Calendar.YEAR);
    Set<EmployeeSkill> skills = Sets.newHashSet(CollectionUtil.pickNRandom(employeeDTO.getSkills(), 1));
    DayOfWeek dayOfWeek = CollectionUtil.pickNRandom(employeeDTO.getDaysAvailable(), 1).get(0);
    LocalDate date = DateUtil.getDateByDow(year, dayOfWeek.getValue());
    EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
    employeeRequestDTO.setDate(date);
    employeeRequestDTO.setSkills(skills);
    return employeeRequestDTO;
  }

  public Set<Long> getEmployeeIds(List<EmployeeDTO> newEmployees, EmployeeRequestDTO eRequest1) {
    return newEmployees.stream()
        .filter(employeeDTO -> employeeDTO.getDaysAvailable().contains(eRequest1.getDate().getDayOfWeek()))
        .filter(employeeDTO -> employeeDTO.getSkills().contains(eRequest1.getSkills().iterator().next()))
        .map(UserDTO::getId)
        .collect(Collectors.toSet());
  }
}
