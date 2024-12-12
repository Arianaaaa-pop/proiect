package com.example.minitor_comm_ms.dtos;

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
    private UUID measurement_id;
    private Timestamp timestamp;
    private UUID deviceId;
    private Double measurementValue;
}
