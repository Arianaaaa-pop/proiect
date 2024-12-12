package com.example.minitor_comm_ms.sevices;

import com.example.minitor_comm_ms.dtos.MeasurementDTO;
import com.example.minitor_comm_ms.dtos.builders.MeasurementBuilder;
import com.example.minitor_comm_ms.entities.Measurement;
import com.example.minitor_comm_ms.repositories.DeviceRepository;
import com.example.minitor_comm_ms.repositories.MeasurementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;


@Service
public class EnergyConsumer {


    @Autowired
    private MeasurementRepository measurementRepository;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MessageConsumer.class);


    public EnergyConsumer(MeasurementRepository measurementRepository) {

        this.measurementRepository = measurementRepository;
    }

    @RabbitListener(queues = "energy-data-queue", ackMode = "AUTO")
    public void receiveEnergyMeasurement(MeasurementDTO measurementDTO) {
        LOGGER.debug("Listener initialized for energy-data-queue.");
        try {
            LOGGER.info("Received message: {}", measurementDTO);
            // Deserializarea mesajului JSON într-un obiect MeasurementDTO

            UUID measurmentId = UUID.randomUUID();
            UUID deviceId = measurementDTO.getDeviceId();
            double measuredvalue = measurementDTO.getMeasurementValue();
            Timestamp timestamp = measurementDTO.getTimestamp();

            Measurement measurement = new Measurement(measurmentId, deviceId, timestamp, measuredvalue);

            measurementRepository.save(measurement);
            LOGGER.info("Saved Measurement with ID: {}", measurement.getMeasurement_id());
        } catch (Exception e) {
            LOGGER.error("Error processing received message: {}", e.getMessage(), e);
        }
        LOGGER.debug("Listener end for energy-data-queue.");
    }




}
