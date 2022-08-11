package com.jourgeois.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
<<<<<<< HEAD
=======

>>>>>>> 208e5155a8d33a2f82635775b47a986919add836
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
<<<<<<< HEAD
=======

>>>>>>> 208e5155a8d33a2f82635775b47a986919add836

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
<<<<<<< HEAD
            // Use a service account
=======
>>>>>>> 208e5155a8d33a2f82635775b47a986919add836
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource("./serviceAccountKey.json").getInputStream());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
