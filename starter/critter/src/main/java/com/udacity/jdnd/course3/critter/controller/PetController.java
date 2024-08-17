package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Pets.
 */

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
  private final PetService service;

  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
    return service.create(petDTO);
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    return service.get(petId);
  }

  @GetMapping
  public List<PetDTO> getPets() {
    return service.getAll();
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    return service.getAllByOwnerId(ownerId);
  }
}
