package com.github.narcispurghel.userservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("user-service").ignoreIfMissing().load();
        dotenv
            .entries()
            .forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                System.out.println(entry);
            });
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
