package com.example.device_ms.controllers;

import com.example.device_ms.dtos.DeviceDTO;
import com.example.device_ms.dtos.UserDTO;
import com.example.device_ms.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/devices")
@CrossOrigin()
public class DeviceController {
    private final DeviceService deviceService;
    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "device-exchange";
    private static final String ROUTING_KEY = "device-data-queue";

    @Autowired
    public DeviceController(DeviceService deviceService, RabbitTemplate rabbitTemplate){
        this.deviceService=deviceService;
        this.rabbitTemplate = rabbitTemplate;
    }
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DeviceController.class);

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        LOGGER.info("Fetching all devices");
        List<DeviceDTO> deviceList = deviceService.findDevices();
        return ResponseEntity.ok(deviceList);  // Returning a list of devices as JSON
    }


    @GetMapping("/user-devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@RequestParam UUID userId) {
        LOGGER.info("Fetching devices for user with ID: {}", userId);
        List<DeviceDTO> devices = deviceService.findDeviceByUserId(userId);
        return ResponseEntity.ok(devices);
    }


    @PostMapping("/insert")
    public ResponseEntity<UUID> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        try {
            LOGGER.info(deviceDTO.toString());
            UUID deviceId = deviceService.insert(deviceDTO);
            LOGGER.info("Inserted device with ID: {}", deviceDTO.getId());

            deviceService.sendDeviceData(deviceDTO);
            LOGGER.info("send message for the device with id", deviceDTO.getId());

          //  deviceService.sendMessage("asta se trimite");
            return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Data integrity violation: {}", e.getMessage(), e); // Log with stack trace
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Unexpected error during insertion: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle generic errors
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable("id") UUID deviceId, @RequestBody DeviceDTO deviceDTO) {
        deviceDTO.setId(deviceId);
        DeviceDTO updatedDevice = deviceService.updateDevice(deviceDTO);
        LOGGER.info("Device with id {} was updated", deviceId);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") UUID deviceId) {
        deviceService.deleteDevice(deviceId);
        LOGGER.info("Deleted device with ID: {}", deviceId);
        return ResponseEntity.ok("Device deleted successfully.");
    }

    //cred ca i bine pt cand se sterge un user sa se stearga si device u
    @DeleteMapping("/delete-by-user/{userId}")
    public ResponseEntity<String> deleteDevicesByUserId(@PathVariable UUID userId) {
        deviceService.deleteDevicesByUserId(userId);
        LOGGER.info("Deleted devices for user with ID: {}", userId);
        return ResponseEntity.ok("Devices deleted successfully for user.");
    }
    @PostMapping("/users")
    public ResponseEntity<String> recieveUsers(@RequestBody List<UserDTO> usersDTO){
        LOGGER.info("the list of users:\n");

        usersDTO.forEach(userDTO -> System.out.println(userDTO.toString()));
        return new ResponseEntity<>("Users received", HttpStatus.OK);

    }

//    @PostMapping ("/sendMessage")
//    public String sendMessage() {
//        // Call the sendMessage method from the service to send the message to the queue
//        deviceService.sendMessage("alo");
//        System.out.println("Sending message " );
//        return "Message sent!";
//    }


    @PostMapping("/test-send")
    public ResponseEntity<String> testSendMessage() {
        try {
            String message = "Test message";
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
            LOGGER.info("Test message sent successfully");
            return ResponseEntity.ok("Test message sent successfully");
        } catch (Exception e) {
            LOGGER.error("Error sending test message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send test message");
        }
    }
    }
