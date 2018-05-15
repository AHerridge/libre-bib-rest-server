package com.aherridge.librebib.server.user;

import com.aherridge.librebib.server.GoogleProperties;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryRequestInitializer;
import com.google.api.services.admin.directory.model.Users;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Service;

@Service
public class GoogleDirectoryService {

  private OAuth2ClientContext oAuth2ClientContext;

  private GoogleProperties googleProperties;

  @Autowired
  public GoogleDirectoryService(GoogleProperties googleProperties,
      OAuth2ClientContext oAuth2ClientContext) {
    this.googleProperties = googleProperties;
    this.oAuth2ClientContext = oAuth2ClientContext;
  }

  public Optional<User> findById(String id) {
    Directory directory = getInstance();

    try {
      return Optional
          .of(convert(directory.users().get("" + id).setViewType("domain_public").execute()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  private List<User> list(String q) {
    Directory directory = getInstance();

    List<User> users = new LinkedList<>();

    try {
      Users result = directory.users().list()
          .setDomain("dublinschools.net")
          .setQuery(q)
          .setMaxResults(10)
          .setViewType("domain_public")
          .execute();

      for (com.google.api.services.admin.directory.model.User user : result.getUsers()) {
        users.add(convert(user));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return users;
  }

  private User convert(com.google.api.services.admin.directory.model.User user) {
    return new User(user.getId(), user.getName().getGivenName(),
        user.getName().getFamilyName(), user.getPrimaryEmail(), user.getThumbnailPhotoUrl());
  }

  private Directory getInstance() {
    //TODO remove this
    return new Directory.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
        getCredential())
        .setDirectoryRequestInitializer(
            new DirectoryRequestInitializer(googleProperties.getApiKey()))
        .setApplicationName(googleProperties.getAppName())
        .build();
  }

  private GoogleCredential getCredential() {
    return new GoogleCredential.Builder()
        .setJsonFactory(JacksonFactory.getDefaultInstance())
        .setTransport(new NetHttpTransport())
        .setClientSecrets(googleProperties.getClientId(), googleProperties.getClientSecret())
        .build()
        .setAccessToken(oAuth2ClientContext.getAccessToken().getValue());
  }
}
