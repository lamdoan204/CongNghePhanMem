package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	 List<Notification> findByOrderOrderID(Integer orderId);
	
	 List<Notification> findByUser_UserId(int userId);
}