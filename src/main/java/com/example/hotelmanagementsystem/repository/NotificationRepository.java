package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Notification;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByUserOrderByDateDesc(User user);
    Long countNotificationByUserAndIsRead(User user, Boolean isRead);

}
