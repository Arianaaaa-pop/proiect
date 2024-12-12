package com.example.minitor_comm_ms.sevices;

import com.example.minitor_comm_ms.dtos.DeviceDTO;
import com.example.minitor_comm_ms.dtos.builders.DeviceBuilder;
import com.example.minitor_comm_ms.entities.Device;
import com.example.minitor_comm_ms.entities.Measurement;
import com.example.minitor_comm_ms.repositories.DeviceRepository;
import com.example.minitor_comm_ms.repositories.MeasurementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Service
public class MessageConsumer {
    private final ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MeasurementRepository measurementRepository;
    private static final  Logger LOGGER = (Logger) LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "device-data-queue")
    public void receiveDescription(DeviceDTO message) {

        System.out.println("Received message: " + message);

        Device device = DeviceBuilder.toEntity(message);
        deviceRepository.save(device);
        System.out.println("Device with id {} was inserted in db"+ device.getId());

    }

//    @RabbitListener(queues = "energy-data-queue")  // Listen to the RabbitMQ queue
//    public void receiveEnergyMeasurement(String measurement) {
//
//        try {
//
//           LOGGER.info("recieved mess: {}",measurement);
//
//            Map<String, Object> jsonMessage = objectMapper.readValue(measurement, Map.class);
//
//            String deviceIdStr = (String) jsonMessage.get("deviceId");
//            UUID deviceId = UUID.fromString(deviceIdStr);
//
//            double measurementValue = (double) jsonMessage.get("measurementValue");
//
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
////            ObjectMapper objectMapper = new ObjectMapper();
////            Measurement measurement1 = objectMapper.readValue(measurement, Measurement.class);
//
//            Measurement measurement1 =new Measurement(UUID.randomUUID(),deviceId,timestamp,measurementValue);
//               measurementRepository.save(measurement1);
//            System.out.println("Saved Measurement with ID: " + measurement1.getId());
//            LOGGER.info("Saved Measurement with ID: " , measurement1.getId());
//        } catch (Exception e) {
//            System.err.println("Error processing received message: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

}
