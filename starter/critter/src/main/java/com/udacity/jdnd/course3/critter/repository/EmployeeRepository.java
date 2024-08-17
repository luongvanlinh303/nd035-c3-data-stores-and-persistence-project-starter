package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends UserRepository<Employee> {
  @Query("SELECT COUNT(e) FROM Employee e " +
      "JOIN e.skills s " +
      "WHERE s = :skill")
  long countBySkill(EmployeeSkill skill);

  @Query("SELECT DISTINCT e FROM Employee e " +
      "WHERE e.id IN :ids")
  Set<Employee> findAllByIds(List<Long> ids);

  @Query("SELECT DISTINCT e FROM Employee e " +
      "JOIN e.skills s " +
      "JOIN e.daysAvailable d " +
      "WHERE s IN :skills AND " +
      "d = :dayOfWeek")
  Set<Employee> getAllBySkillsAvailability(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);
}
