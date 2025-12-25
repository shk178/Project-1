package com.foodtruck.pos.foodtruck_pos_v1.common.event;

import org.springframework.context.ApplicationEventPublisher;

import java.util.Objects;

public class EventPublisher {
    private static ApplicationEventPublisher eventPublisher;
    static void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        EventPublisher.eventPublisher = eventPublisher;
    }
    public static void publish(Object event) {
        Objects.requireNonNull(eventPublisher);
        eventPublisher.publishEvent(event);
    }
}
