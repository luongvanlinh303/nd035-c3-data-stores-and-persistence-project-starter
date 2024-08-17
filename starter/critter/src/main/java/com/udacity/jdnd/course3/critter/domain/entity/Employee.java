package com.udacity.jdnd.course3.critter.domain.entity;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "Employee")
public class Employee extends User {
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "Skill", joinColumns = @JoinColumn(name = "employee_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "skill")
  private Set<EmployeeSkill> skills;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "DayOfWeek", joinColumns = @JoinColumn(name = "employee_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "day_of_week")
  private Set<DayOfWeek> daysAvailable;

  @ManyToMany(mappedBy = "employees")
  private Set<Event> event;
}
