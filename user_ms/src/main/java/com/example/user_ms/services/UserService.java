package com.example.user_ms.services;


import com.example.user_ms.dtos.UserDTO;
import com.example.user_ms.dtos.builders.UserBuilder;
import com.example.user_ms.entities.Role;
import com.example.user_ms.entities.User;
import com.example.user_ms.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  RestTemplate restTemplate;

    @Value("${DEVICE_URL}")
    private String deviceUrl;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public List<UserDTO> findUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole()))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));
        return UserBuilder.toUserDTO(user);
    }

    public UUID insert(UserDTO userDTO) {
        LOGGER.info("Inserting user: {}", userDTO); // Log the incoming DTO
        User user= UserBuilder.toEntity(userDTO);
       user= userRepository.save(user);
        LOGGER.info("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), userDTO.getEmail(), userDTO.getPassword(), user.getRole());
    }



    public void deleteUser(UUID userId) {
        String url = deviceUrl+"/devices/delete-by-user/"+userId ;
        restTemplate.delete(url);
        userRepository.deleteById(userId);
    }

  public String sendData() {
      List<UserDTO> userDTOS =findUsers();
      String url = deviceUrl+"/devices/users";
      return restTemplate.postForObject(url,userDTOS,String.class);
  }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }


}