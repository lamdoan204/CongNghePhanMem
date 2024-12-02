package com.Project.CongNghePhanMem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Project.CongNghePhanMem.Service.MessagingService;
import com.Project.CongNghePhanMem.dto.Message;
import com.google.firebase.database.DatabaseReference;

@RestController
@RequestMapping("/api/messages")
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

    @GetMapping("/listen/{userId}")
    public void listenForMessages(@PathVariable String userId) {
        messagingService.listenForMessages(userId);
    }

    @GetMapping("/listen/all")
    public void listenForAllMessages() {
        messagingService.listenForAllMessages();
    }
}
