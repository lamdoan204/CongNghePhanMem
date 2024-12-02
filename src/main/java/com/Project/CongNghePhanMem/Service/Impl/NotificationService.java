package com.Project.CongNghePhanMem.Service.Impl;


import com.Project.CongNghePhanMem.Entity.Notification;
import com.Project.CongNghePhanMem.Repository.NotificationRepository;
import com.Project.CongNghePhanMem.Service.INotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Lấy tất cả thông báo của người dùng theo userId
    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        // Sử dụng phương thức repository để lấy thông báo của người dùng
        return notificationRepository.findByUser_UserId(userId);
    }

    // Lấy tất cả thông báo
    @Override
    public List<Notification> getAllNotifications() {
        // Trả về tất cả thông báo từ repository
        return notificationRepository.findAll();
    }

    // Lưu thông báo
    @Override
    public Notification saveNotification(Notification notification) {
        // Lưu thông báo vào cơ sở dữ liệu
        return notificationRepository.save(notification);
    }

    // Lấy thông báo theo ID
    @Override
    public Notification getNotificationById(int notificationId) {
        // Tìm thông báo theo ID
        return notificationRepository.findById(notificationId).orElse(null);
    }

    // Xóa thông báo theo ID
    @Override
    public void deleteNotification(int notificationId) {
        // Xóa thông báo theo ID
        notificationRepository.deleteById(notificationId);
    }
}
