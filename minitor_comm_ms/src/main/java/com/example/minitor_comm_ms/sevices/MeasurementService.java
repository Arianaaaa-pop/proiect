package com.example.minitor_comm_ms.sevices;
import com.example.minitor_comm_ms.dtos.MeasurementDTO;
import com.example.minitor_comm_ms.dtos.builders.MeasurementBuilder;
import com.example.minitor_comm_ms.entities.Measurement;
import com.example.minitor_comm_ms.repositories.MeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    // Insert a new measurement into the database
    public UUID insert(MeasurementDTO measurementDTO) {
        Measurement measurement = MeasurementBuilder.toEntity(measurementDTO);
        measurement = measurementRepository.save(measurement);
        LOGGER.info("Measurement with id {} was inserted into the db", measurement.getMeasurement_id());
        return measurement.getMeasurement_id();
    }

    // Fetch all measurements
    public List<MeasurementDTO> findAllMeasurements() {
        List<Measurement> measurementList = measurementRepository.findAll();
        return measurementList.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }

//    // Fetch measurements by deviceId
//    public List<MeasurementDTO> findMeasurementsByDeviceId(UUID deviceId) {
//        List<Measurement> measurementList = measurementRepository.findByDeviceId(deviceId);
//        return measurementList.stream()
//                .map(MeasurementBuilder::toMeasurementDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Fetch measurements within a specific time range
//    public List<MeasurementDTO> findMeasurementsByTimestampRange(String startTime, String endTime) {
//        List<Measurement> measurementList = measurementRepository.findByTimestampBetween(startTime, endTime);
//        return measurementList.stream()
//                .map(MeasurementBuilder::toMeasurementDTO)
//                .collect(Collectors.toList());
//    }
}
