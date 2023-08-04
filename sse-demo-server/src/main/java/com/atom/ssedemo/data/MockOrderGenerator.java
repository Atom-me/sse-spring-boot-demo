package com.atom.ssedemo.data;

import com.atom.ssedemo.events.NewOrderEvent;
import com.atom.ssedemo.models.Order;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时生成订单信息
 */
@Component
public class MockOrderGenerator {

    private static final int INITIAL_DELAY_SECONDS = 0;
    private static final int GENERATION_INTERVAL_SECONDS = 10;

    private static int lastGeneratedId = 0;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void init() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::publishNewOrderEvent, INITIAL_DELAY_SECONDS, GENERATION_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private void publishNewOrderEvent() {
        // 生成mock数据
        Faker faker = new Faker();
        Order order = new Order(++lastGeneratedId, faker.name().fullName(), faker.food().ingredient());
        NewOrderEvent customSpringEvent = new NewOrderEvent(this, order);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

}