package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
  long countById(Long id);

  @Query("SELECT COUNT(p) FROM Pet p " +
      "JOIN p.owner c " +
      "WHERE c.id = :customerId")
  long countByCustomerId(Long customerId);

  @Query("SELECT DISTINCT p FROM Pet p " +
      "WHERE p.id IN :petIds")
  Set<Pet> findAllByIds(List<Long> petIds);

  @Query("SELECT p FROM Pet p " +
      "JOIN p.owner o " +
      "WHERE o.id = :ownerId")
  List<Pet> getAllByOwnerId(Long ownerId);

  Optional<Pet> findByName(String name);
}
