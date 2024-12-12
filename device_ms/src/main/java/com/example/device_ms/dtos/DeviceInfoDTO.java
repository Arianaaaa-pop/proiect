package com.example.device_ms.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceInfoDTO  implements Serializable {
    private String address;
    private String description;
    private int mhec;
}
