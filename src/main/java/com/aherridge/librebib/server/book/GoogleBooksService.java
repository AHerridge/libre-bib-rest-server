package com.aherridge.librebib.server.book;

import com.aherridge.librebib.server.GoogleProperties;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleBooksService {

  private Books BOOKS;

  @Autowired
  public GoogleBooksService(GoogleProperties googleProperties) {
    try {
      BOOKS =
          new Books.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
              .setApplicationName(googleProperties.getAppName())
              .setGoogleClientRequestInitializer(
                  new BooksRequestInitializer(googleProperties.getApiKey()))
              .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Optional<Book> findById(String id) {
    try {
      return Optional.of(convert(BOOKS.volumes().get("").setVolumeId(id).execute()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  public List<Book> findAllByTitle(String title) {
    return findAll("title:" + title);
  }

  private Optional<Book> find(String q) {
    Optional<Book> book = Optional.empty();

    List<Book> queryResult = findAll(q);
    if (!queryResult.isEmpty()) {
      book = Optional.of(queryResult.get(0));
    }

    return book;
  }

  private List<Book> findAll(String q) {
    List<Book> books = new LinkedList<>();

    try {
      for (Volume volume : BOOKS.volumes().list(q).execute().getItems()) {
        books.add(convert(volume));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(books);
    return books;
  }

  private Book convert(@NotNull Volume volume) {
    VolumeInfo info = volume.getVolumeInfo();

    String id = info.getIndustryIdentifiers().get(0).getIdentifier();
    String title = info.getTitle();
    String[] authors = info.getAuthors().toArray(new String[info.getAuthors().size()]);
    String description =
        info.getDescription().substring(0, Math.min(info.getDescription().length(), 255));
    String coverLink = (String) info.getImageLinks().get("thumbnail");

    return new Book(id, title, authors, description, coverLink);
  }
}
