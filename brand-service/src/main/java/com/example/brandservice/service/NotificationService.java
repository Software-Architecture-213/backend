package com.example.brandservice.service;

import com.example.brandservice.model.FavouritePromotions;
import com.example.brandservice.model.Notification;
import com.example.brandservice.model.Promotion;
import com.example.brandservice.repository.FavouritePromotionsRepository;
import com.example.brandservice.repository.NotificationRepository;
import com.example.brandservice.utility.DateUtility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FavouritePromotionsRepository favouritePromotionsRepository;

    @Transactional
    public Notification notifyUsers(UUID userId) {
        // Lấy FavouritePromotions của userId cụ thể
        FavouritePromotions favouritePromotions = favouritePromotionsRepository.findByUserId(userId)
                .orElse(null);

        if (favouritePromotions != null) {
            // Lấy danh sách các promotion yêu thích của user
            List<Promotion> promotions = favouritePromotions.getPromotions();

            // Kiểm tra các promotion có sắp diễn ra không (2-3 ngày tới)
            List<Promotion> upcomingPromotions = promotions.stream()
                    .filter(promotion -> {
                        LocalDateTime startDate = DateUtility.convertDateToLocalDateTime(promotion.getStartDate());
                        LocalDateTime now = LocalDateTime.now();
                        // return !startDate.isBefore(now) && startDate.isBefore(now.plusDays(0));
                        return !startDate.isBefore(now);
                    })
                    .toList();

            // Tạo và gửi thông báo cho mỗi promotion sắp diễn ra
            for (Promotion promotion : upcomingPromotions) {
                Notification notification = new Notification();
                notification.setId(UUID.randomUUID().toString());
                notification.setUserid(userId);
                notification.setMessage(String.format("Promotion '%s' starts on %s",
                        promotion.getName(),
                        promotion.getStartDate()));
                notification.setType("PROMOTION");
                notification.setIsread(false);
                notification.setCreatedAt(LocalDateTime.now());
                notification.setUpdatedAt(LocalDateTime.now());

                // Lưu notification vào DB
                return notificationRepository.save(notification);

                // // Gửi thông báo qua WebSocket nếu user đang kết nối
                // webSocketHandler.sendNotification(notification);
            }
        }
        return null;
    }
}
