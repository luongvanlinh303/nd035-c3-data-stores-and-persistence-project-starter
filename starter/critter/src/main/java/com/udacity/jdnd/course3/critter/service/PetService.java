package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.AlreadyExistsException;
import com.udacity.jdnd.course3.critter.exception.NotFoundException;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {
  private final ModelMapper mapper;
  private final PetRepository repository;

  private void validateForCreate(PetDTO petDTO) {
    long count = repository.countById(petDTO.getId());
    if (count == 1) {
      throw new AlreadyExistsException(String.format("ID %d already exists", petDTO.getId()));
    }
  }

  public PetDTO create(PetDTO petDTO) {
    validateForCreate(petDTO);
    Pet pet = mapper.map(petDTO, Pet.class);
    pet = repository.save(pet);
    petDTO.setId(pet.getId());

    return petDTO;
  }

  public PetDTO get(long id) {
    return repository.findById(id)
        .map(pet -> mapper.map(pet, PetDTO.class))
        .orElseThrow(() -> new NotFoundException(String.format("ID %d not found", id)));
  }

  public List<PetDTO> getAll() {
    return repository.findAll().stream()
        .map(pet -> mapper.map(pet, PetDTO.class))
        .collect(Collectors.toList());
  }

  public List<PetDTO> getAllByOwnerId(long ownerId) {
    return repository.getAllByOwnerId(ownerId).stream()
        .map(pet -> mapper.map(pet, PetDTO.class))
        .collect(Collectors.toList());
  }
}
