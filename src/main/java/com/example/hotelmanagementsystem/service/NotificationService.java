package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public Page<Notification> findPaginated(int pageNo, int pageSize);
}
