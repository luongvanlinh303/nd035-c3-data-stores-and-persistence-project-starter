package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.ControllerService;
import com.udacity.jdnd.course3.critter.service.DTOService;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 * <p>
 * These tests should all pass once the project is complete.
 */

@Transactional
@ContextConfiguration(classes = CritterApplication.class)
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

  @Autowired
  private ControllerService controllerService;

  @Autowired
  private DTOService dtoService;

  @Test
  public void testCreateCustomer() {
    controllerService.createCustomer();
  }

  @Test
  public void testCreateEmployee() {
    controllerService.createEmployee();
  }

  @Test
  public void testAddPetsToCustomer() {
    CustomerDTO newCustomer = controllerService.createCustomer();
    controllerService.createPet(newCustomer);
  }

  @Test
  public void testFindPetsByOwner() {
    CustomerDTO newCustomer = controllerService.createCustomer();
    controllerService.createPets(2, newCustomer);
  }

  @Test
  public void testFindOwnerByPet() {
    CustomerDTO newCustomer = controllerService.createCustomer();
    PetDTO newPet = controllerService.createPet(newCustomer);
    controllerService.flushAndClear();

    CustomerDTO owner = controllerService.findCustomerByPet(newPet);

    Assertions.assertEquals(owner.getId(), newCustomer.getId());
    Assertions.assertEquals(owner.getPetIds().get(0), newPet.getId());
  }

  @Test
  public void testChangeEmployeeAvailability() {
    EmployeeDTO employeeDTO = dtoService.generateEmployee(null);
    Assertions.assertNotNull(employeeDTO.getDaysAvailable());

    final Set<DayOfWeek> daysAvailable = employeeDTO.getDaysAvailable();
    employeeDTO.setDaysAvailable(null);
    EmployeeDTO newEmployee = controllerService.saveEmployee(employeeDTO);
    Assertions.assertNull(newEmployee.getDaysAvailable());

    controllerService.updateEmployeeAvailability(daysAvailable, newEmployee);
    EmployeeDTO retrievedEmployee = controllerService.findEmployeeById(newEmployee);
    Assertions.assertEquals(daysAvailable, retrievedEmployee.getDaysAvailable());
  }

  @Test
  public void testFindEmployeesByServiceAndTime() {
    List<EmployeeDTO> newEmployees = controllerService.saveEmployees(null, null, null);

    //make a request that matches employee 1 or 2
    EmployeeRequestDTO eRequest1 = dtoService.generateEmployeeRequestDTO(newEmployees.get(0));
    EmployeeRequestDTO eRequest2 = dtoService.generateEmployeeRequestDTO(newEmployees.get(1));

    Set<Long> eIds1 = controllerService.findEmployeeIdsByService(eRequest1);
    Set<Long> eIds1Expected = dtoService.getEmployeeIds(newEmployees, eRequest1);
    Assertions.assertEquals(eIds1, eIds1Expected);

    //make a request that matches only employee 3
    Set<Long> eIds2 = controllerService.findEmployeeIdsByService(eRequest2);
    Set<Long> eIds2Expected = dtoService.getEmployeeIds(newEmployees, eRequest2);
    Assertions.assertEquals(eIds2, eIds2Expected);
  }

  @Test
  public void testSchedulePetsForServiceWithEmployee() {
    EmployeeDTO employeeDTO = controllerService.saveEmployees((Long) null).get(0);
    CustomerDTO customerDTO = controllerService.saveCustomers((Long) null).get(0);
    PetDTO petDTO = controllerService.savePets(new ImmutablePair<>(null, customerDTO.getId())).get(0);

    ScheduleDTO scheduleDTO = dtoService.generateSchedule(null,
        Collections.singleton(EmployeeSkill.FEEDING),
        Collections.singletonList(petDTO.getId()),
        Collections.singletonList(employeeDTO.getId()),
        Collections.singletonList(customerDTO.getId()));
    ScheduleDTO retrievedSchedule = controllerService.saveSchedule(scheduleDTO);

    Assertions.assertEquals(scheduleDTO.getActivities(), retrievedSchedule.getActivities());
    Assertions.assertEquals(scheduleDTO.getDate(), retrievedSchedule.getDate());
    Assertions.assertEquals(scheduleDTO.getEmployeeIds(), retrievedSchedule.getEmployeeIds());
    Assertions.assertEquals(scheduleDTO.getPetIds(), retrievedSchedule.getPetIds());
  }

  @Test
  public void testFindScheduleByEntities() {
    ScheduleDTO schedule1 = controllerService.autoSaveSchedule(1, 1, 2);
    ScheduleDTO schedule2 = controllerService.autoSaveSchedule(1, 3, 1);
    //add a third schedule that shares some employees and pets with the other schedules
    ScheduleDTO schedule3 = controllerService.saveSchedule(dtoService.generateSchedule(null,
        Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING),
        schedule2.getPetIds(),
        schedule1.getEmployeeIds(),
        schedule2.getCustomerIds()));

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

    // Employee 1 in is both schedule 1 and 3
    List<ScheduleDTO> schedules1 = controllerService.getSchedulesForEmployee(schedule1);
    controllerService.compareSchedules(schedule1, schedules1.get(0));
    controllerService.compareSchedules(schedule3, schedules1.get(1));

    // Employee 2 is only in Schedule 2
    List<ScheduleDTO> schedules2 = controllerService.getSchedulesForEmployee(schedule2);
    controllerService.compareSchedules(schedule2, schedules2.get(0));

    // Pet 1 is only in Schedule 1
    List<ScheduleDTO> schedulesPet1 = controllerService.getSchedulesForPet(schedule1);
    controllerService.compareSchedules(schedule1, schedulesPet1.get(0));

    // Pet from Schedule 2 is in both Schedules 2 and 3
    List<ScheduleDTO> schedulesPet2 = controllerService.getSchedulesForPet(schedule2);
    controllerService.compareSchedules(schedule2, schedulesPet2.get(0));
    controllerService.compareSchedules(schedule3, schedulesPet2.get(1));

    // Owner of the first Pet will only be in Schedule 1
    List<ScheduleDTO> scheduleCustomer1 = controllerService.getSchedulesForCustomer(schedule1);
    controllerService.compareSchedules(schedule1, scheduleCustomer1.get(0));

    // Owner of Pet from Schedule 2 will be in both Schedules 2 and 3
    List<ScheduleDTO> scheduleCustomer2 = controllerService.getSchedulesForCustomer(schedule2);
    controllerService.compareSchedules(schedule2, scheduleCustomer2.get(0));
    controllerService.compareSchedules(schedule3, scheduleCustomer2.get(1));
  }
}
