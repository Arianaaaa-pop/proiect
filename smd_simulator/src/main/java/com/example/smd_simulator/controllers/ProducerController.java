package com.example.smd_simulator.controllers;

import com.example.smd_simulator.service.MessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final MessageProducer messageProducer;

    public ProducerController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }


//
//    // Endpoint pentru procesarea și trimiterea datelor din CSV
//    @GetMapping("/sendCsvData")
//    public String sendCsvData(@RequestParam String filePath) {
//        try {
//            messageProducer.sendCsvData(filePath);
//            return "CSV data sent!";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to send CSV data: " + e.getMessage();
//        }
//    }


//    public void sendCsvData() throws Exception {
//      messageProducer.readAndSendCSVData();
//    }
}