package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.dto.UserDTO;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControllerService {
  @Autowired
  private UserController userController;

  @Autowired
  private PetController petController;

  @Autowired
  private ScheduleController scheduleController;

  @Autowired
  private DTOService dtoService;

  @PersistenceContext
  private EntityManager em;

  public void flushAndClear() {
    em.flush();
    em.clear();
  }

  public CustomerDTO createCustomer() {
    CustomerDTO generatedCustomer = dtoService.generateCustomer(null);
    CustomerDTO newCustomer = userController.saveCustomer(generatedCustomer);
    CustomerDTO retrievedCustomer = userController.getAllCustomers().get(0);
    Assertions.assertEquals(newCustomer.getName(), generatedCustomer.getName());
    Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
    Assertions.assertTrue(retrievedCustomer.getId() > 0);

    return retrievedCustomer;
  }

  public EmployeeDTO createEmployee() {
    EmployeeDTO generatedEmployee = dtoService.generateEmployee(null);
    EmployeeDTO newEmployee = userController.saveEmployee(generatedEmployee);
    EmployeeDTO retrievedEmployee = userController.getEmployee(newEmployee.getId());
    Assertions.assertEquals(generatedEmployee.getSkills(), newEmployee.getSkills());
    Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
    Assertions.assertEquals(retrievedEmployee.getId(), generatedEmployee.getId());

    return retrievedEmployee;
  }

  public PetDTO createPet(CustomerDTO customerDTO) {
    PetDTO petDTO = dtoService.generatePet(null, customerDTO.getId());
    PetDTO newPet = petController.savePet(petDTO);

    //make sure pet contains customer id
    PetDTO retrievedPet = petController.getPet(newPet.getId());
    Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
    Assertions.assertEquals(retrievedPet.getOwnerId(), customerDTO.getId());

    //make sure you can retrieve pets by owner
    retrievedPet = petController.getPetsByOwner(customerDTO.getId()).get(0);
    Assertions.assertEquals(newPet.getId(), retrievedPet.getId());
    Assertions.assertEquals(newPet.getName(), retrievedPet.getName());

    flushAndClear();
    customerDTO = userController.getAllCustomers().get(0);

    //check to make sure customer now also contains pet
    Assertions.assertNotNull(customerDTO.getPetIds());
    Assertions.assertTrue(customerDTO.getPetIds().size() > 0);
    Assertions.assertEquals(customerDTO.getPetIds().get(0), retrievedPet.getId());

    return retrievedPet;
  }

  public void createPets(int count, CustomerDTO customerDTO) {
    List<PetDTO> petDTOs = dtoService.batchPet(
        IntStream.range(0, count)
            .mapToObj(i -> new ImmutablePair((Long) null, customerDTO.getId()))
            .toArray(Pair[]::new));

    List<PetDTO> newPets = petDTOs.stream()
        .map(petDTO -> petController.savePet(petDTO))
        .collect(Collectors.toList());
    List<PetDTO> retrievedPets = petController.getPetsByOwner(customerDTO.getId());

    Assertions.assertEquals(retrievedPets.size(), count);
    for (int i = 0; i < retrievedPets.size(); i++) {
      Assertions.assertEquals(retrievedPets.get(i).getOwnerId(), customerDTO.getId());
      Assertions.assertEquals(retrievedPets.get(i).getId(), newPets.get(i).getId());
    }
  }

  public void compareSchedules(ScheduleDTO schedule1, ScheduleDTO schedule2) {
    Assertions.assertEquals(
        schedule1.getPetIds().stream().sorted().collect(Collectors.toList()),
        schedule2.getPetIds().stream().sorted().collect(Collectors.toList()));
    Assertions.assertEquals(
        schedule1.getEmployeeIds().stream().sorted().collect(Collectors.toList()),
        schedule2.getEmployeeIds().stream().sorted().collect(Collectors.toList()));
    Assertions.assertEquals(
        schedule1.getCustomerIds().stream().sorted().collect(Collectors.toList()),
        schedule2.getCustomerIds().stream().sorted().collect(Collectors.toList()));
    Assertions.assertEquals(
        schedule1.getActivities().stream().sorted().collect(Collectors.toList()),
        schedule2.getActivities().stream().sorted().collect(Collectors.toList()));
    Assertions.assertEquals(schedule1.getDate(), schedule2.getDate());
  }

  public List<EmployeeDTO> saveEmployees(Long... ids) {
    return dtoService.batchEmployee(ids).stream()
        .map(employeeDTO -> userController.saveEmployee(employeeDTO))
        .collect(Collectors.toList());
  }

  public List<CustomerDTO> saveCustomers(Long... ids) {
    return dtoService.batchCustomer(ids).stream()
        .map(customerDTO -> userController.saveCustomer(customerDTO))
        .collect(Collectors.toList());
  }

  public List<PetDTO> savePets(Pair<Long, Long>... ids) {
    return dtoService.batchPet(ids).stream()
        .map(petDTO -> petController.savePet(petDTO))
        .collect(Collectors.toList());
  }

  public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
    return scheduleController.createSchedule(scheduleDTO);
  }

  public List<PetDTO> autoSavePets(int petCount, List<CustomerDTO> customers) {
    return dtoService.batchPets(petCount, customers).stream()
        .map(pet -> petController.savePet(pet))
        .collect(Collectors.toList());
  }

  public List<EmployeeDTO> autoSaveEmployees(int employeeCount) {
    return dtoService.batchEmployees(employeeCount).stream()
        .map(employee -> userController.saveEmployee(employee))
        .collect(Collectors.toList());
  }

  public List<CustomerDTO> autoSaveCustomers(int customerCount) {
    return dtoService.batchCustomers(customerCount).stream()
        .map(customer -> userController.saveCustomer(customer))
        .collect(Collectors.toList());
  }

  public ScheduleDTO autoSaveSchedule(int customerCount, int employeeCount, int petCount) {
    List<CustomerDTO> customers = autoSaveCustomers(customerCount);
    List<EmployeeDTO> employees = autoSaveEmployees(employeeCount);
    List<PetDTO> pets = autoSavePets(petCount, customers);

    return scheduleController.createSchedule(dtoService.generateSchedule(
        null,
        pets.stream().map(PetDTO::getId).distinct().collect(Collectors.toList()),
        employees.stream().map(UserDTO::getId).distinct().collect(Collectors.toList()),
        customers.stream().map(UserDTO::getId).distinct().collect(Collectors.toList())));
  }

  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    return userController.saveEmployee(employeeDTO);
  }

  public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, EmployeeDTO newEmployee) {
    userController.setAvailability(daysAvailable, newEmployee.getId());
  }

  public EmployeeDTO findEmployeeById(EmployeeDTO newEmployee) {
    return userController.getEmployee(newEmployee.getId());
  }

  public Set<Long> findEmployeeIdsByService(EmployeeRequestDTO eRequest1) {
    return userController.findEmployeesForService(eRequest1).stream()
        .map(EmployeeDTO::getId)
        .collect(Collectors.toSet());
  }

  public CustomerDTO findCustomerByPet(PetDTO newPet) {
    return userController.getOwnerByPet(newPet.getId());
  }

  public List<ScheduleDTO> getSchedulesForCustomer(ScheduleDTO schedule) {
    return scheduleController.getScheduleForCustomer(
        userController.getOwnerByPet(schedule.getPetIds().get(0)).getId());
  }

  public List<ScheduleDTO> getSchedulesForPet(ScheduleDTO schedule) {
    return scheduleController.getScheduleForPet(schedule.getPetIds().get(0));
  }

  public List<ScheduleDTO> getSchedulesForEmployee(ScheduleDTO schedule) {
    return scheduleController.getScheduleForEmployee(schedule.getEmployeeIds().get(0));
  }
}
