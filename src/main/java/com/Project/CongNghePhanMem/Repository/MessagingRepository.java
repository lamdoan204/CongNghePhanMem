package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.CongNghePhanMem.Entity.Message;

public interface MessagingRepository extends JpaRepository<Message, Long>{
	List<Message> findByChatId(Long chatId);	
}
