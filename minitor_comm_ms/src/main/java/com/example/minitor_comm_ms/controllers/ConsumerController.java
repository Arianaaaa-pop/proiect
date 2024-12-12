package com.example.minitor_comm_ms.controllers;

import com.example.minitor_comm_ms.dtos.DeviceDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
public class ConsumerController {
    private final RabbitTemplate rabbitTemplate;

    public ConsumerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/fetchMessage")
    public String fetchMessage() {
        DeviceDTO message =  (DeviceDTO) rabbitTemplate.receiveAndConvert("device-data-queue");
        if (message != null) {
            System.out.println("Fetched message: " + message);
            return "Fetched message: " + message;
        } else {
            return "No messages available in the queue.";
        }
    }

}
