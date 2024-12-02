package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.Notification;

public interface INotificationService {

	 // Lấy tất cả thông báo của người dùng theo userId
    List<Notification> getNotificationsByUserId(int userId);

    // Lấy tất cả thông báo
    List<Notification> getAllNotifications();

    // Lưu thông báo mới
    Notification saveNotification(Notification notification);

    // Tìm thông báo theo ID
    Notification getNotificationById(int notificationId);

    // Xóa thông báo
    void deleteNotification(int notificationId);
}

