package com.example.device_ms.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeviceDTO {
    private UUID id;
    private UUID userId;
    private String description;
    private String address;
    private int mhec;
}


