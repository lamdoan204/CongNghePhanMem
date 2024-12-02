package com.Project.CongNghePhanMem.Controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.CongNghePhanMem.Entity.Message;
import com.Project.CongNghePhanMem.Service.Impl.MessagingService;
import com.google.firebase.database.DatabaseReference;

@RestController
@RequestMapping("/messages")
public class MessagingController {
	@Autowired
    private MessagingService messagingService;
    @PostMapping("/send/{userId}")
    public void sendMessage(@PathVariable String userId, @RequestBody Message message) {
        messagingService.sendMessage(userId, message);
    }
    @GetMapping("/receive/{userId}")
    public DatabaseReference receiveMessages(@PathVariable String userId) {
        return messagingService.getMessages(userId);
    }
    @GetMapping("/receive/all")
    public DatabaseReference receiveAllMessages() {
        return messagingService.getAllMessages();
    }
}
