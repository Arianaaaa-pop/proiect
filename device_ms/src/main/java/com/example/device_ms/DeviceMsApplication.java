package com.example.device_ms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class DeviceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceMsApplication.class, args);
    }

}
