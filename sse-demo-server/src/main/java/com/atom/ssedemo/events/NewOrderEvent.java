package com.atom.ssedemo.events;

import com.atom.ssedemo.models.Order;
import org.springframework.context.ApplicationEvent;

public class NewOrderEvent extends ApplicationEvent {

    private final Order order;

    public NewOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}