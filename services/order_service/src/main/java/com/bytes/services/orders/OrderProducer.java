package com.bytes.services.orders;

import com.bytes.services.contracts.events.order.v1.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderProducer {

    private final JmsTemplate jmsTemplate;
    private final String ordersQueue;
    private final ObjectMapper objectMapper;

    public OrderProducer(JmsTemplate jmsTemplate,
                         @Value("${events.orders.created.v1}") String ordersQueue,
                         ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.ordersQueue = ordersQueue;
        this.objectMapper = objectMapper;
    }

    public void send(OrderCreatedEvent orderCreatedEvent) {
        String json = objectMapper.writeValueAsString(orderCreatedEvent);
        jmsTemplate.convertAndSend(ordersQueue, json);
    }
}
