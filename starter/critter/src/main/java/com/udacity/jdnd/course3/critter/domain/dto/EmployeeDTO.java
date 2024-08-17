package com.udacity.jdnd.course3.critter.domain.dto;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */

@Getter
@Setter
public class EmployeeDTO extends UserDTO {
  private Set<EmployeeSkill> skills;
  private Set<DayOfWeek> daysAvailable;
}
