package com.example.device_ms.services;

import com.example.device_ms.dtos.DeviceDTO;
import com.example.device_ms.dtos.DeviceInfoDTO;
import com.example.device_ms.dtos.UserDTO;
import com.example.device_ms.dtos.builders.DeviceBuilder;
import com.example.device_ms.entities.Device;
import com.example.device_ms.repo.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final RestTemplate restTemplate;
    private final DeviceRepository deviceRepository;
    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DeviceService.class);
    private static final String EXCHANGE_NAME = "device-exchange";
    private static final String ROUTING_KEY = "devices_routing_key";

    @Value("${USER_URL}")
    private String userURL;

    @Autowired
    private Queue queue;


    public DeviceService(RestTemplate restTemplate, DeviceRepository deviceRepository, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.deviceRepository=deviceRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public UUID insert(DeviceDTO deviceDTO ){

        UserDTO userDTO = getUserById(deviceDTO.getUserId());
        LOGGER.info("ceva nu ibine aici???");
        if (userDTO == null) {
            throw new RuntimeException("User not found for ID: " + deviceDTO.getUserId());
        }

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
       // sendDeviceData(deviceDTO);
        LOGGER.info("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public List<DeviceDTO> findDeviceByUserId(UUID userId) {
        List<Device> deviceList = deviceRepository.findByUserId(userId);
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(UUID deviceId) {
        LOGGER.info("Device with id {} was found in db", deviceId);
        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
        Device device = optionalDevice.get();
        return DeviceBuilder.toDeviceDTO(device);
    }


    public DeviceDTO updateDevice(DeviceDTO deviceDTO) {
        LOGGER.info("User with id {} was updated in db", deviceDTO.getId());
        Device existingDevice = deviceRepository.findById(deviceDTO.getId()).get();
        existingDevice.setDescription(deviceDTO.getDescription());
        existingDevice.setMhec(deviceDTO.getMhec());
        Device updatedDevice = deviceRepository.save(existingDevice);
        return DeviceBuilder.toDeviceDTO(updatedDevice);
    }

    public void deleteDevice(UUID deviceId) {
        LOGGER.info("User with id {} was deleted from db", deviceId);
        deviceRepository.deleteById(deviceId);
    }

    public UserDTO getUserById(UUID userId) {
        String url = userURL+ "/users/" + userId;
        return restTemplate.getForObject(url, UserDTO.class);
    }


    public void deleteDevicesByUserId(UUID userId) {
        List<Device> devices = deviceRepository.findByUserId(userId);
        for (Device device : devices) {
            deviceRepository.delete(device);
            LOGGER.info("Deleted device with ID: {}", device.getId());
        }
    }

    public void sendDeviceData(DeviceDTO deviceDTO) {
//        DeviceInfoDTO device=new DeviceInfoDTO();
//        device.setDescription(deviceDTO.getDescription());
//        device.setAddress(deviceDTO.getAddress());
//        device.setMhec(deviceDTO.getMhec());
//

        LOGGER.info("Sending message to queue: {}", deviceDTO);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, deviceDTO);
        System.out.println("Sent message: " + deviceDTO);

    }


    public void sendMessage(String message) {
        System.out.println("Sending message to RabbitMQ: " + message);  // Log message
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }


}
