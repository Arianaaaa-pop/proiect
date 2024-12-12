package com.example.user_ms.dtos;
import com.example.user_ms.entities.Role;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;
}