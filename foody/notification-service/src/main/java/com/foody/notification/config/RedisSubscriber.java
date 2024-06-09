package com.foody.notification.config;

import com.foody.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final NotificationService notificationService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Am from redis listener");
        String response = new String(message.getBody());
        String[] parts = response.split(":");
        String orderId = parts[0];
        boolean accepted = Boolean.parseBoolean(parts[1]);

        // Handle the response
        notificationService.respondToOrder(orderId, accepted);
    }
}
