package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends UserRepository<Customer> {
  @Query("SELECT COUNT(c) FROM Customer c " +
      "JOIN c.pets p " +
      "WHERE p.id = :petId")
  long countByPetId(Long petId);

  @Query("SELECT p.owner FROM Pet p " +
      "JOIN p.owner c " +
      "JOIN c.pets p2 " +
      "WHERE p.id = :petId")
  Optional<Customer> getByPetId(Long petId);

  @Query("SELECT DISTINCT c FROM Customer c " +
      "WHERE c.id IN :ids")
  Set<Customer> findAllByIds(List<Long> ids);
}
