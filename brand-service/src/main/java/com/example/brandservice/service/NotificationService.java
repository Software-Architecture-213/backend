package com.example.brandservice.service;

import com.example.brandservice.handler.NotificationWebSocketHandler;
import com.example.brandservice.model.Notification;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.repository.NotificationRepository;
import com.example.brandservice.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationWebSocketHandler webSocketHandler;
    private final PromotionRepository promotionRepository;
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 9 * * *") // Check daily at 9 AM
    public void notifyUsers() {
        List<Promotion> promotions = promotionRepository.findPromotionsStartingSoon(2, 3);

        for (Promotion promotion : promotions) {
            Notification notification = new Notification();
            notification.setId(UUID.randomUUID().toString());
            notification.setMessage(String.format("Promotion '%s' starts on %s",
                    promotion.getName(),
                    promotion.getStartDate()));
            notification.setType("PROMOTION");
            notification.setIsread(false);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setUpdatedAt(LocalDateTime.now());

            // Save notification in the database
            notificationRepository.save(notification);

            // Send notification to WebSocket if the user is connected
            webSocketHandler.sendNotification(notification);
        }
    }
}

