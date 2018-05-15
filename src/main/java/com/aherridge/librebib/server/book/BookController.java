package com.aherridge.librebib.server.book;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class BookController {

  private BookRepository bookRepository;
  private GoogleBooksService googleBooksService;

  @Autowired
  public BookController(BookRepository bookRepository, GoogleBooksService googleBooksService) {
    this.bookRepository = bookRepository;
    this.googleBooksService = googleBooksService;
  }

  @RequestMapping(method = GET, value = "/books/{id}")
  @ResponseBody
  public Optional<Book> findById(@PathVariable("id") String id) {
    Optional<Book> book = bookRepository.findById(id);

    if (!book.isPresent()) {
      book = googleBooksService.findById(id);
    }

    return book;
  }
}
