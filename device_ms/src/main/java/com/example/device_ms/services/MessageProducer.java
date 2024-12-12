package com.example.device_ms.services;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    private static final String EXCHANGE_NAME = "device-exchange";
    private static final String ROUTING_KEY = "device-data-queue";
    public void sendMessage(String message) {
        System.out.println("Sending message to RabbitMQ: " + message);  // Log message
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }




}
