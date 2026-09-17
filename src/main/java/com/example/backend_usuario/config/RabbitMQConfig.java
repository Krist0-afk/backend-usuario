package com.example.backend_usuario.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    // Nombres de la tubería
    public static final String EXCHANGE_NAME = "usuario.exchange";
    public static final String QUEUE_NAME = "usuario.creado.queue";
    public static final String ROUTING_KEY = "usuario.creado.key";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // true = la cola sobrevive reinicios
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        // Conecta la cola con el exchange usando la routing key
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        // Permite que Spring convierta automáticamente el objeto Java a JSON
        return new Jackson2JsonMessageConverter();
    }
}
