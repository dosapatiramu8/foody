package com.foody.notification.firebase.config;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        FirebaseInitializer.initializeFirebase();
    }
}
