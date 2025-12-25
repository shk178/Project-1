package com.foodtruck.pos.foodtruck_pos_v1.common.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    private final ApplicationEventPublisher applicationEventPublisher;
    public EventConfig(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @Bean
    public InitializingBean initializeEventPublisher() {
        return () -> EventPublisher.setEventPublisher(applicationEventPublisher);
    }
}
