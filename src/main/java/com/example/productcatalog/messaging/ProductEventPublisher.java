package com.example.productcatalog.messaging;

import com.example.productcatalog.model.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private static final String EXCHANGE = "product-events-exchange";
    private static final String ROUTING_KEY = "product.events";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishCreated(Product product) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, "CREATED:" + product.getId() + ":" + product.getName());
    }

    public void publishDeleted(Long id) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, "DELETED:" + id);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}