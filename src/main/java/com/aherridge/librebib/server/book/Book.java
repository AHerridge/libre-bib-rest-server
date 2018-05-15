package com.aherridge.librebib.server.book;

import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

  @Id
  private String id;

  private String title;

  private String[] authors;

  private String description;

  private String coverLink;

  public Book() {
  }

  public Book(String id, String title, String[] authors, String description, String coverLink) {
    this.id = id;
    this.title = title;
    this.authors = authors;
    this.description = description;
    this.coverLink = coverLink;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String[] getAuthors() {
    return authors;
  }

  public void setAuthors(String[] authors) {
    this.authors = authors;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCoverLink() {
    return coverLink;
  }

  public void setCoverLink(String coverLink) {
    this.coverLink = coverLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(id, book.id) &&
        Objects.equals(title, book.title) &&
        Arrays.equals(authors, book.authors) &&
        Objects.equals(description, book.description) &&
        Objects.equals(coverLink, book.coverLink);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, title, description, coverLink);
    result = 31 * result + Arrays.hashCode(authors);
    return result;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", authors=" + Arrays.toString(authors) +
        ", description='" + description + '\'' +
        ", coverLink='" + coverLink + '\'' +
        '}';
  }
}
