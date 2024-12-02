package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.CongNghePhanMem.Entity.ChatBox;
import com.Project.CongNghePhanMem.Entity.User;

public interface ChatRepository extends JpaRepository<ChatBox, Long> {
    ChatBox findByUser1AndUser2(User user1, User user2);
}
