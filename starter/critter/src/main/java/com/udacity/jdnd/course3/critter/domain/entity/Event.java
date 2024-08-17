package com.udacity.jdnd.course3.critter.domain.entity;

import com.udacity.jdnd.course3.critter.domain.contract.BaseIdEntity;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
public class Event implements BaseIdEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String name;
  private LocalDate date;
  private LocalTime start;
  private LocalTime end;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "Activity", joinColumns = @JoinColumn(name = "event_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "activity")
  private Set<EmployeeSkill> activities;

  @ManyToMany(cascade = CascadeType.ALL)
  @Where(clause = "DTYPE='Employee'")
  private Set<Employee> employees;

  @ManyToMany(cascade = CascadeType.ALL)
  @Where(clause = "DTYPE='Customer'")
  private Set<Customer> customers;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Pet> pets;
}
