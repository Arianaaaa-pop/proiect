package com.example.smd_simulator.service;

import com.example.smd_simulator.entities.Measurement;
import com.example.smd_simulator.entities.MeasurementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.rabbitmq.client.*;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    private static final String DEVICE_ID = "1a848c75-f275-4f1c-94d3-f2a097a4e965";
    private static final String FILE_PATH = "C:\\AAAAA Facultate\\AN IV\\sistemeDistribuite\\smd_simulator\\src\\main\\resources\\sensor.csv";
    private static final String ENERGY_EXCHANGE_NAME = "device-exchange";
    private static final String ENERGY_ROUTING_KEY = "energy_routing_key";

    private final ObjectMapper objectMapper;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }
//
//    @Scheduled(fixedDelay = 60000)
//    public void sendCsvData() {
//
//
//    String filePath="C:\\AAAAA Facultate\\AN IV\\sistemeDistribuite\\smd_simulator\\src\\main\\resources\\sensor.csv";
//
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                long timestamp = System.currentTimeMillis();
//                String measurementValue = nextLine[0];
//
//                Measurement measurement = new Measurement();
//                measurement.setDeviceId(UUID.fromString(DEVICE_ID));
//                measurement.setMeasurementValue(Double.parseDouble(measurementValue));
//                measurement.setTimestamp(String.valueOf(timestamp));  // Set timestamp as long
//
//                ObjectMapper objectMapper=new ObjectMapper();
//                String message=objectMapper.writeValueAsString(measurement);
//
//                // Send the message to RabbitMQ
//                rabbitTemplate.convertAndSend( ENERGY_EXCHANGE_NAME,ENERGY_ROUTING_KEY, message);
//                System.out.println("Sent message: " + message);
//
//
//            }
//        } catch (Exception e) {
//            System.err.println("Error sending data: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
////
//private String convertObjectToJson(Object object) throws IOException {
//    ObjectMapper objectMapper = new ObjectMapper();
//    return objectMapper.writeValueAsString(object);
//}
//
//    public void sendMeasurementData(MeasurementDTO measurementDTO) throws IOException {
//        LOGGER.info("Sending measurement data to RabbitMQ: {}", measurementDTO);
//        rabbitTemplate.convertAndSend(ENERGY_EXCHANGE_NAME, ENERGY_ROUTING_KEY, convertObjectToJson(measurementDTO));
//    }
//
//    @Scheduled(fixedDelay = 60000)
//    public void readAndSendCSVData() throws Exception {
//          UUID deviceId = UUID.fromString("1a848c75-f275-4f1c-94d3-f2a097a4e965");
//        String filePath="C:\\AAAAA Facultate\\AN IV\\sistemeDistribuite\\smd_simulator\\src\\main\\resources\\sensor.csv";
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            List<String[]> rows = reader.readAll();
//
//            for (String[] row : rows) {
//                // The row[0] contains the measurement value (no timestamp or deviceId in the CSV)
//                double measurementValue = Double.parseDouble(row[0]);
//
//                // Generate the current timestamp
//                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//                // Create the MeasurementDTO object
//                MeasurementDTO measurementDTO = new MeasurementDTO(
//                        measurementValue,               // Current timestamp
//                        timestamp,                // The fixed or generated deviceId
//                        deviceId         // Measurement value from the CSV
//                                );
//
//               sendMeasurementData(measurementDTO);
//               System.out.println("Sent message: " + measurementDTO);
//
//
//            }
//        }
//    }


    @Scheduled(fixedDelay = 60000)
    public void readAndSendCSVData() {

        UUID deviceId = UUID.fromString(DEVICE_ID);


        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                double measurementValue = Double.parseDouble(row[0]);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                MeasurementDTO measurementDTO = new MeasurementDTO(
                        measurementValue,
                        timestamp,
                        deviceId
                );

                sendMeasurementData(measurementDTO);
            }
        } catch (Exception e) {
            LOGGER.error("Error reading and sending CSV data: {}", e.getMessage(), e);
        }
    }

    private void sendMeasurementData(MeasurementDTO measurementDTO) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5673);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            System.out.println("Conexiune stabilită cu succes!");

            // Declararea exchange-ului și a cozii
            channel.exchangeDeclare("device-exchange", BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare("energy-data-queue", true, false, false, null);
            channel.queueBind("energy-data-queue", "device-exchange", "energy_routing_key");
            String message = objectMapper.writeValueAsString(measurementDTO);
            channel.basicPublish("device-exchange", "energy_routing_key", null, message.getBytes());
            LOGGER.info("Sent message: {}", message);
        } catch (Exception e) {
            LOGGER.error("Error sending measurement data: {}", e.getMessage(), e);
        }
    }

}
