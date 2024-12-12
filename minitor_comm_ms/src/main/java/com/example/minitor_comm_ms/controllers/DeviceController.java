package com.example.minitor_comm_ms.controllers;

import com.example.minitor_comm_ms.dtos.DeviceDTO;
import com.example.minitor_comm_ms.sevices.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/insert")
    public ResponseEntity<UUID> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        try {
            LOGGER.info(deviceDTO.toString());
            UUID deviceId = deviceService.insert(deviceDTO);
            LOGGER.info("Inserted device with ID: {}", deviceDTO.getId());

            LOGGER.info("send message for the device with id", deviceDTO.getId());

            return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Data integrity violation: {}", e.getMessage(), e); // Log with stack trace
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Unexpected error during insertion: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle generic errors
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        LOGGER.info("Fetching all devices");
        List<DeviceDTO> deviceList = deviceService.findDevices();
        return ResponseEntity.ok(deviceList);  // Returning a list of devices as JSON
    }


}
