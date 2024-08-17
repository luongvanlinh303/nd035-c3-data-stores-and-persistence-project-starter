package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final CustomerService customerService;
  private final EmployeeService employeeService;

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    return customerService.create(customerDTO);
  }

  @GetMapping("/customer/{customerId}")
  public CustomerDTO getCustomer(@PathVariable long customerId) {
    return customerService.get(customerId);
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    return customerService.getAll();
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) {
    return customerService.getByPetId(petId);
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    return employeeService.create(employeeDTO);
  }

  @GetMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable long employeeId) {
    return employeeService.get(employeeId);
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
    employeeService.setAvailability(daysAvailable, employeeId);
  }

  @GetMapping("/employee/availability")
  public Set<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
    return employeeService.getAllBySkillsAvailability(employeeRequestDTO);
  }
}
