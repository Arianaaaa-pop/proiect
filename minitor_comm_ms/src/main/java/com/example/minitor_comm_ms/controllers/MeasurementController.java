package com.example.minitor_comm_ms.controllers;

import com.example.minitor_comm_ms.dtos.MeasurementDTO;
import com.example.minitor_comm_ms.sevices.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MeasurementController.class);

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    // Endpoint for inserting a new measurement
    @PostMapping("/insert")
    public  ResponseEntity<UUID> insertMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        try {
            LOGGER.info(measurementDTO.toString());
            UUID measurementId = measurementService.insert(measurementDTO);
            LOGGER.info("Inserted device with ID: {}", measurementDTO.getMeasurement_id());

            LOGGER.info("send message for the device with id", measurementDTO.getMeasurement_id());

            return new ResponseEntity<>(measurementId, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Data integrity violation: {}", e.getMessage(), e); // Log with stack trace
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Unexpected error during insertion: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle generic errors
        }
    }

    // Endpoint for fetching all measurements
    @GetMapping("/all")
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> measurementDTOList =measurementService.findAllMeasurements();
    return ResponseEntity.ok(measurementDTOList);
    }

//    // Endpoint for fetching measurements by deviceId
//    @GetMapping("/device/{deviceId}")
//    public List<MeasurementDTO> getMeasurementsByDeviceId(@PathVariable UUID deviceId) {
//        return measurementService.findMeasurementsByDeviceId(deviceId);
//    }
//
//    // Endpoint for fetching measurements by timestamp range
//    @GetMapping("/range")
//    public List<MeasurementDTO> getMeasurementsByTimestampRange(
//            @RequestParam String startTime,
//            @RequestParam String endTime) {
//        return measurementService.findMeasurementsByTimestampRange(startTime, endTime);
//    }
}
