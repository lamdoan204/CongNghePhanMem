package com.Project.CongNghePhanMem.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

	private final DatabaseReference databaseReference;

	public MessagingService(FirebaseApp firebaseApp) {
		this.databaseReference = FirebaseDatabase.getInstance(firebaseApp).getReference("root");
	}

	public void sendMessage(String userId, com.Project.CongNghePhanMem.dto.Message message) {
		databaseReference.child(userId).push().setValueAsync(message);
	}

	public DatabaseReference getMessages(String userId) {
		return databaseReference.child(userId);
	}

	public DatabaseReference getAllMessages() {
		return databaseReference;
	}

	public void listenForMessages(String userId) {
		databaseReference.child(userId).addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
				com.Project.CongNghePhanMem.dto.Message message = dataSnapshot
						.getValue(com.Project.CongNghePhanMem.dto.Message.class);
				System.out.println("[Role: User] New message received: " + message.getContent());
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.err.println("Failed to listen for messages: " + databaseError.getMessage());
			}
		});
	}

	public void listenForAllMessages() {
		databaseReference.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
				for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
					com.Project.CongNghePhanMem.dto.Message message = userSnapshot
							.getValue(com.Project.CongNghePhanMem.dto.Message.class);
					System.out.println("New message received from user: " + message.getContent());
				}
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
				for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
					com.Project.CongNghePhanMem.dto.Message message = userSnapshot
							.getValue(com.Project.CongNghePhanMem.dto.Message.class);
					System.out.println("Message updated from user: " + message.getContent());
				}
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
					com.Project.CongNghePhanMem.dto.Message message = userSnapshot
							.getValue(com.Project.CongNghePhanMem.dto.Message.class);
					System.out.println("Message removed from user: " + message.getContent());
				}
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.err.println("Failed to listen for messages: " + databaseError.getMessage());
			}
		});
	}

}
