package com.aherridge.librebib.server.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Contract {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String userId;

  private String bookId;

  @JsonFormat(pattern = "MM-dd-yyyy")
  private LocalDate dueDate;

  public Contract() {
  }

  public Contract(String userId, String bookId, LocalDate dueDate) {
    this.userId = userId;
    this.bookId = bookId;
    this.dueDate = dueDate;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getBookId() {
    return this.bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public LocalDate getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Contract contract = (Contract) o;
    return Objects.equals(id, contract.id) &&
        Objects.equals(userId, contract.userId) &&
        Objects.equals(bookId, contract.bookId) &&
        Objects.equals(dueDate, contract.dueDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, bookId, dueDate);
  }

  @Override
  public String toString() {
    return "Contract{" +
        "id='" + id + '\'' +
        ", userId='" + userId + '\'' +
        ", bookId='" + bookId + '\'' +
        ", dueDate=" + dueDate +
        '}';
  }
}
