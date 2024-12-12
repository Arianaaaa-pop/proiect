package com.example.smd_simulator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private UUID deviceId;
    private Double measurementValue;
    private String timestamp;

//
//    @Override
//    public String toString() {
//        return "{" +
//                "deviceId=" + deviceId +
//                ", measurementValue=" + measurementValue +
//                ", timestamp='" + timestamp + '\'' +
//                '}';
//    }
}
