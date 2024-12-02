package com.Project.CongNghePhanMem.Service.Impl;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.Project.CongNghePhanMem.Entity.Message;
import com.google.firebase.FirebaseApp;
import org.springframework.stereotype.Service;
@Service
public class MessagingService {
    private final DatabaseReference databaseReference;
    public MessagingService(FirebaseApp firebaseApp) {
        this.databaseReference = FirebaseDatabase.getInstance(firebaseApp).getReference("root");
    }
    public void sendMessage(String userId, Message message) {
        databaseReference.child(userId).push().setValueAsync(message);
    }
    public DatabaseReference getMessages(String userId) {
        return databaseReference.child(userId);
    }
    public DatabaseReference getAllMessages() {
        return databaseReference;
    }
}