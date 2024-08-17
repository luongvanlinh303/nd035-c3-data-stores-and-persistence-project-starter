package com.udacity.jdnd.course3.critter.domain.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "Customer")
public class Customer extends User {
  private String notes;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private Set<Pet> pets;

  @ManyToMany(mappedBy = "customers")
  private Set<Event> event;
}
