package com.udacity.jdnd.course3.critter.domain.entity;

import com.udacity.jdnd.course3.critter.domain.contract.BaseIdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PetType implements BaseIdEntity<Long> {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type;
}
