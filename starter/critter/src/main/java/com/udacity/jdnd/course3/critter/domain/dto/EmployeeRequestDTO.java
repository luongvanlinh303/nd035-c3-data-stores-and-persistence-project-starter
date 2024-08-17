package com.udacity.jdnd.course3.critter.domain.dto;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */

@Getter
@Setter
public class EmployeeRequestDTO {
  private Set<EmployeeSkill> skills;
  private LocalDate date;
}
