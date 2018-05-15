package com.aherridge.librebib.server.book;

import com.aherridge.librebib.server.GoogleProperties;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleBooksService {

  private Books books;

  @Autowired
  public GoogleBooksService(GoogleProperties googleProperties) {
    try {
      books = new Books.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), null)
          .setApplicationName(googleProperties.getAppName())
          .setGoogleClientRequestInitializer(
              new BooksRequestInitializer(googleProperties.getApiKey()))
          .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Optional<Book> findById(String id) {
    Optional<Book> book = Optional.empty();

    List<Volume> results = query("isbn=" + id);

    if (!results.isEmpty()) {
      book = Optional.of(convert(results.get(0)));
    }

    return book;
  }

  public Optional<Book> findByTitle(String title) {
    Optional<Book> book = Optional.empty();

    List<Volume> results = query("title=" + title);

    if (!results.isEmpty()) {
      book = Optional.of(convert(results.get(0)));
    }

    return book;
  }

  private List<Volume> query(String q) {
    try {
      return books.volumes().list(q).execute().getItems();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new LinkedList<>();
  }

  private Book convert(Volume volume) {
    VolumeInfo info = volume.getVolumeInfo();

    String id = info.getIndustryIdentifiers().get(0).getIdentifier();
    String title = info.getTitle();
    String[] authors = info.getAuthors().toArray(new String[info.getAuthors().size()]);
    String description = info.getDescription()
        .substring(0, Math.min(info.getDescription().length(), 255));
    String coverLink = (String) info.getImageLinks().get("thumbnail");

    return new Book(id, title, authors, description, coverLink);
  }
}
