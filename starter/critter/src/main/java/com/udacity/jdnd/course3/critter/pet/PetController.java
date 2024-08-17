package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
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
public class PetController {

  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    throw new UnsupportedOperationException();
  }

  @GetMapping
  public List<PetDTO> getPets() {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    throw new UnsupportedOperationException();
  }
}
