package com.Project.CongNghePhanMem.Controller.employee;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.ChatBox;
import com.Project.CongNghePhanMem.Entity.Message;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.ChatRepository;
import com.Project.CongNghePhanMem.Repository.MessagingRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;

@Controller
@RequestMapping("/employee/chat")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessagingRepository messageRepository;

    // Hiển thị chatbox
    @GetMapping("/{customerId}")
    public String showChat(@PathVariable Integer customerId, Model model) {
        User employee = userRepository.findByUsername("employeeUsername"); // Tên nhân viên đang đăng nhập
        User customer = userRepository.findByUserId(customerId); // Tìm khách hàng theo customerId

        if (customer == null) {
            return "error"; // Nếu không tìm thấy khách hàng
        }

        // Tìm cuộc trò chuyện giữa nhân viên và khách hàng
        ChatBox chat = chatRepository.findByUser1AndUser2(employee, customer);
        if (chat == null) {
            // Tạo một cuộc trò chuyện mới nếu chưa có
            chat = new ChatBox();
            chat.setUser1(employee);
            chat.setUser2(customer);
            chatRepository.save(chat);
        }

        model.addAttribute("chat", chat);
        model.addAttribute("messages", chat.getMessages()); // Lấy tin nhắn của cuộc trò chuyện

        return "employee/chatbox";
    }

    // Gửi tin nhắn
    @PostMapping("/send/{customerId}")
    public String sendMessage(@PathVariable String customerId, @RequestParam String messageContent, RedirectAttributes redirectAttributes) {
        User employee = userRepository.findByUsername("employeeUsername"); // Tên nhân viên đang đăng nhập
        User customer = userRepository.findByUsername(customerId);

        // Tìm cuộc trò chuyện giữa nhân viên và khách hàng
        ChatBox chat = chatRepository.findByUser1AndUser2(employee, customer);
        if (chat == null) {
            // Nếu không tìm thấy cuộc trò chuyện, tạo một cuộc trò chuyện mới
            chat = new ChatBox();
            chat.setUser1(employee);
            chat.setUser2(customer);
            chatRepository.save(chat);
        }

        // Tạo và lưu tin nhắn
        Message message = new Message();
        message.setSender(employee);
        message.setReceiver(customer);
        message.setContent(messageContent);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);

        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/employee/chat/{customerId}";
    }
}
