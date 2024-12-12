package com.example.minitor_comm_ms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Constants for the energy data exchange and queue
   // private static final String ENERGY_EXCHANGE_NAME = "energy-exchange";
    private static final String ENERGY_ROUTING_KEY = "energy-data-queue";
    private static final String DEVICE_EXCHANGE_NAME = "device-exchange";
    private static final String DEVICE_ROUTING_KEY = "device-data-queue";


    @Bean
    public Queue deviceDataQueue() {
        return new Queue(DEVICE_ROUTING_KEY, true);
    }
    @Bean
    public Queue energyDataQueue() {
        return new Queue(ENERGY_ROUTING_KEY, true);
    }


   // @Bean
//    public DirectExchange deviceExchange() {
//        return new DirectExchange(DEVICE_EXCHANGE_NAME);
//    }
//    @Bean
//    public DirectExchange energyExchange() {
//        return new DirectExchange(ENERGY_EXCHANGE_NAME);
//    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DEVICE_EXCHANGE_NAME, true, false);
    }


    @Bean
    public Binding bindingDeviceData( Queue deviceDataQueue, DirectExchange directExchange)
    {
        return new Binding("device-data-queue", Binding.DestinationType.QUEUE, "device-exchange", "devices_routing_key", null);
    }

    @Bean
    public Binding bindingEnergyData( Queue energyDataQueue,DirectExchange directExchange) {
        return new Binding("energy-data-queue", Binding.DestinationType.QUEUE, "device-exchange", "energy_routing_key", null);
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Conversie JSON
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


}
