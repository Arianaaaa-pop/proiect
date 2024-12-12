package com.example.smd_simulator.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitConfig {
//    @Bean
//    public MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("energy-exchange");
//    }
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue())
//                .to(directExchange())
//                .with("energy-data-queue");
//    }
//
//    @Bean
//    public Queue queue() {
//        return new Queue("energy-data-queue", true);
//    }


@Value("${simulator.rabbitmq.caching.connection.factory.hostname}")
private String hostname;

@Value("${simulator.rabbitmq.readings.exchange}")
private String readingsExchange;
@Value("${simulator.rabbitmq.readings.queue}")
private String readingsQueue;
@Value("${simulator.rabbitmq.readings.routing_key}")
private String readingsRoutingKey;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(hostname);
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory cachingConnectionFactory) {
        return new RabbitTemplate(cachingConnectionFactory);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(readingsQueue())
                .to(directExchange())
                .with("energy-data-queue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("device-exchange");
    }

    @Bean
    public Queue readingsQueue() {
        return new Queue(readingsQueue);
    }




}
