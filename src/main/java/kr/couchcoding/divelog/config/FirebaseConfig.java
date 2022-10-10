package kr.couchcoding.divelog.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("Initializing Firebase.");
        FileInputStream serviceAccount = new FileInputStream("./firebaseAdminKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        log.info("FirebaseApp initialized: {}" , app.getName());

        return app;
    }

    // FirebaseAuth를 초기화 한다
    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp());
        return firebaseAuth;
    }
    
    // @Bean
    // public Bucket bucket() throws IOException {
    //     return StorageClient.getInstance(firebaseApp()).bucket();
    // }
}
