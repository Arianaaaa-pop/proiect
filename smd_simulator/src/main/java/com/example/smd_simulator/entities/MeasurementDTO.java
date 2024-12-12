package com.example.smd_simulator.entities;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeasurementDTO {
    private Double measurementValue;
    private Timestamp timestamp;
    private UUID deviceId;
}
