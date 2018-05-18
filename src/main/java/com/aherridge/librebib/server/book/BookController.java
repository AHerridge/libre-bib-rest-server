package com.aherridge.librebib.server.book;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

  @RequestMapping(method = GET, value = "/books/search/findAllByTitle/{title}")
  @ResponseBody
  public Collection<Book> findAllByTitle(@PathVariable("title") String title) {
    Collection<Book> books = new LinkedList<>(bookRepository.findAllByTitle(title));
    books.addAll(googleBooksService.findAllByTitle(title));

    LoggerFactory.getLogger(this.getClass()).error(books.toString());
    return books;
  }
}
