package com.Project.CongNghePhanMem.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ChatBox {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1; // Người tham gia đầu tiên (ví dụ: khách hàng)

    @ManyToOne
    private User user2; // Người tham gia thứ hai (ví dụ: nhân viên)

    @OneToMany(mappedBy = "chat")
    private List<Message> messages; // Danh sách tin nhắn trong cuộc trò chuyện

 // Lấy tin nhắn cuối cùng
    public Message getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(messages.size() - 1); // Tin nhắn cuối cùng trong danh sách
        }
        return null;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
