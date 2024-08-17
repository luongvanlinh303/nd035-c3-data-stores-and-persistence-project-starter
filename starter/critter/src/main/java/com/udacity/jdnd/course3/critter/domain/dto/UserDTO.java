package com.udacity.jdnd.course3.critter.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private long id;
  private String name;
  private String phoneNumber;
  private String emailAddress;
  private String city;
  private String fullAddress;
}
