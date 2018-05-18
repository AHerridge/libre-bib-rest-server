package com.aherridge.librebib.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String thumbnail;
}
