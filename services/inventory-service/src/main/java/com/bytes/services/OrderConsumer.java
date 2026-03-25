package com.bytes.services;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @JmsListener(destination = "${events.orders.created.v1}")
    public void receive(String json) {
        System.out.println("Received message: \n" + json);
    }
}
