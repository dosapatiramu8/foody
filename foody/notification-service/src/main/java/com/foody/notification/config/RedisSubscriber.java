package com.foody.notification.config;

import com.foody.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber implements MessageListener {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String response = new String(message.getBody());
        String[] parts = response.split(":");
        String orderId = parts[0];
        boolean accepted = Boolean.parseBoolean(parts[1]);

        // Handle the response
        notificationService.respondToOrder(orderId, accepted);
    }
}
