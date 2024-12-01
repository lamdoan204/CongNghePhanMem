package com.Project.CongNghePhanMem.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/win-project-dcfd0-firebase-adminsdk-33on6-7d849f15d3.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://win-project-dcfd0-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
