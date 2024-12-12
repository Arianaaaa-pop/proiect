package com.example.minitor_comm_ms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MinitorCommMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinitorCommMsApplication.class, args);
    }

}
