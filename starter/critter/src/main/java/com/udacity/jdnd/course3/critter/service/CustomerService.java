package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.AlreadyExistsException;
import com.udacity.jdnd.course3.critter.exception.NotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService extends UserService<Customer, CustomerDTO, CustomerRepository> {
  private final ModelMapper mapper;
  private final PetRepository petRepository;

  public CustomerService(
      ModelMapper mapper,
      CustomerRepository repository,
      PetRepository petRepository
  ) {
    super(repository);
    this.mapper = mapper;
    this.petRepository = petRepository;
  }

  @Override
  public void validateForCreate(CustomerDTO customerDTO) {
    super.validateForCreate(customerDTO);
    if (customerDTO.getPetIds() != null) {
      customerDTO.getPetIds()
          .forEach(petId -> {
            long count = petRepository.countById(petId);
            if (count != 1) {
              throw new NotFoundException(String.format("Pet ID %d does not exist", petId));
            }
          });
      customerDTO.getPetIds()
          .forEach(petId -> {
            long count = repository.countByPetId(petId);
            if (count != 0) {
              throw new AlreadyExistsException(String.format("Pet ID %d already owned by a Customer", petId));
            }
          });
    }
  }

  public CustomerDTO create(CustomerDTO customerDTO) {
    validateForCreate(customerDTO);
    Customer customer = mapper.map(customerDTO, Customer.class);
    customer = repository.save(customer);
    customerDTO.setId(customer.getId());

    return customerDTO;
  }

  public CustomerDTO get(long id) {
    return repository.findById(id)
        .map(customer -> mapper.map(customer, CustomerDTO.class))
        .orElseThrow(() -> new NotFoundException(String.format("ID %d not found", id)));
  }

  public List<CustomerDTO> getAll() {
    List<Customer> customers = repository.findAll();

    return customers.stream()
        .map(customer -> mapper.map(customer, CustomerDTO.class))
        .collect(Collectors.toList());
  }

  public CustomerDTO getByPetId(long petId) {
    Customer owner = repository.getByPetId(petId)
        .orElseThrow(() -> new NotFoundException("Owner does not exist"));

    return mapper.map(owner, CustomerDTO.class);
  }
}
