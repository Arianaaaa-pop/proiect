package com.example.device_ms.dtos;

import com.example.device_ms.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String password;
   private Role role;
}