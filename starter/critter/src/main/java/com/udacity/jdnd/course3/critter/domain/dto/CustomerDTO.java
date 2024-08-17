package com.udacity.jdnd.course3.critter.domain.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */

@Getter
@Setter
public class CustomerDTO extends UserDTO {
  private long id;
  private String name;
  private String phoneNumber;
  private String notes;
  private List<Long> petIds;
}
