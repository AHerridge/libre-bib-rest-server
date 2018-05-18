package com.aherridge.librebib.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RepositoryRestController
public class UserController {

  private UserRepository userRepository;
  private GoogleDirectoryService googleDirectoryService;

  @Autowired
  public UserController(UserRepository userRepository, GoogleDirectoryService googleDirectoryService) {
    this.userRepository = userRepository;
    this.googleDirectoryService = googleDirectoryService;
  }

  @RequestMapping(method = GET, value = "/users/{id}")
  @ResponseBody
  public Optional<User> findById(@PathVariable("id") String id) {
    Optional<User> user = userRepository.findById(id);

    if (!user.isPresent())
      user = googleDirectoryService.findById(id);

    return user;
  }

  @RequestMapping(method = GET, value = "/users/search/findByEmail/{email}")
  @ResponseBody
  public Optional<User> findByEmail(@PathVariable("email") String email) {
    Optional<User> user = userRepository.findByEmail(email);

    if (!user.isPresent())
      user = googleDirectoryService.findByEmail(email);

    return user;
  }
}
