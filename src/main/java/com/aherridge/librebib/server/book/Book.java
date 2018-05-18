package com.aherridge.librebib.server.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id private String id;

  private String title;

  private String[] authors;

  private String description;

  private String thumbnail;
}
