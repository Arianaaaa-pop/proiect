package com.example.user_ms.controllers;


import com.example.user_ms.dtos.UserDTO;
import com.example.user_ms.entities.Role;
import com.example.user_ms.entities.User;
import com.example.user_ms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin()
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserService.class);

    // Retrieve all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        LOGGER.info("Finding all users");
        List<UserDTO> list = userService.findUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Create a new user
    @PostMapping("/insert")
    public ResponseEntity<UUID> insertUser(@RequestBody UserDTO userDTO) {
      try{
          UUID userId = userService.insert(userDTO);
          LOGGER.info("User with id {} was created", userId);
          return new ResponseEntity<>(userId, HttpStatus.CREATED);
      } catch (DataIntegrityViolationException e) {
          LOGGER.error("Error inserting user: {}", e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") UUID userId) {
        LOGGER.info("Fetching user with id {}", userId);
        UserDTO userDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Update an existing user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID userId, @RequestBody UserDTO userDTO) {
        userDTO.setId(userId); // Ensure the DTO has the correct ID
        UserDTO updatedUser = userService.update(userDTO);
        LOGGER.info("User with id {} was updated", userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID userId) {
        userService.deleteUser(userId);
        LOGGER.info("User with id {} was deleted", userId);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendData(){
        userService.sendData();
        return new ResponseEntity<>("user sended",HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> registerUser(@RequestBody UserDTO userDTO) {
        userDTO.setRole(Role.CLIENT);
        try {
            UUID userId = userService.insert(userDTO);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        User user = userService.findByEmail(userDTO.getEmail());
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            // Create a UserDTO to return to the frontend
            UserDTO responseDTO = new UserDTO();
            responseDTO.setId(user.getId());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setRole(user.getRole());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


}