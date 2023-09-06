package ru.bot.romanmessageapibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RomanMessageApiBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RomanMessageApiBotApplication.class, args);
    }

}
