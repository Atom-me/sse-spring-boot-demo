package com.atom.ssedemo.controllers;

import com.atom.ssedemo.events.NewOrderEvent;
import com.atom.ssedemo.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;


/**
 * SseEmitter是SpringMVC(4.2+)提供的一种技术,它是基于Http协议的，
 * 相比WebSocket，它更轻量，但是它只能从服务端向客户端单向发送信息。在SpringBoot中我们无需引用其他jar就可以使用。
 */
@Controller
public class OrdersController implements ApplicationListener<NewOrderEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);
    private final Map<SseEmitter, Queue<Order>> clientQueues = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @GetMapping("/orders-sse")
    @CrossOrigin
    public SseEmitter streamOrders() {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出warning：AsyncRequestTimeoutException
        SseEmitter emitter = new SseEmitter(0L);
        Queue<Order> queue = new ConcurrentLinkedQueue<>();
        clientQueues.put(emitter, queue);

        // 注册回调
        emitter.onError(ex -> {
            LOGGER.info("连接异常...", ex);
            clientQueues.remove(emitter);
        });

        emitter.onCompletion(() -> {
            LOGGER.info("连接结束...");
            clientQueues.remove(emitter);
        });

        emitter.onTimeout(() -> {
            LOGGER.info("连接超时...");
            clientQueues.remove(emitter);
        });


        executor.scheduleAtFixedRate(() -> {
            queue.forEach(order -> {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data(order)
                        .name("new-order");
                try {
                    emitter.send(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // 清空队列中的元素
            queue.clear();

        }, 0, 1, TimeUnit.SECONDS);

        return emitter;
    }

    @Override
    public void onApplicationEvent(NewOrderEvent newOrderEvent) {
        clientQueues.keySet().forEach(emitter -> {
            Queue<Order> queue = clientQueues.get(emitter);
            queue.add(newOrderEvent.getOrder());
        });
    }
}