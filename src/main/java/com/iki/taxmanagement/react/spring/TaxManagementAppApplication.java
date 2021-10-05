package com.iki.taxmanagement.react.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * 
 * @author dannymunoz
 *
 */

@SpringBootApplication
public class TaxManagementAppApplication {

    public static void main(String[] args) throws IOException {

        try {

            ClassLoader classLoader = TaxManagementAppApplication.class.getClassLoader();

            File file = new File(
                    Objects.requireNonNull(classLoader.getResource("firebaseServiceAccount.json")).getFile());

            FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://taxmanagement-5dbe7-default-rtdb.firebaseio.com").build();

            FirebaseApp.initializeApp(options);

            SpringApplication.run(TaxManagementAppApplication.class, args);

        }
        catch (Exception e) {
            System.out.println("EXCEPTION ================= " + e);
        }

    }

}
