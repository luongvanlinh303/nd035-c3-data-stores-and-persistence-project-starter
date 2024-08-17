package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
  long countById(Long id);

  long countByPhoneNumber(String phoneNumber);

  long countByEmailAddress(String emailAddress);

  Optional<T> findByPhoneNumber(String phoneNumber);

  Optional<T> findByEmailAddress(String emailAddress);
}
