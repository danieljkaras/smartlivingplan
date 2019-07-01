package com.danielkaras.smartlivingplan.controller;

import com.danielkaras.smartlivingplan.exception.ResourceNotFoundException;
import com.danielkaras.smartlivingplan.model.User;
import com.danielkaras.smartlivingplan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> showAllUsers(){
        logger.info("searching data about all users.");
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createNewUser(@Valid @RequestBody User user) {
        logger.info("creating entry for new user. user email: {}", user.getEmail());
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUserFromRepository(@PathVariable Long userId){
        logger.info("receive request to delete user from repository. user id is: {}", userId);

        return userRepository.findById(userId)
                .map(user -> {
                    logger.info("deleting user with id: {}", userId);
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " is not found!!!"));
    }
}
