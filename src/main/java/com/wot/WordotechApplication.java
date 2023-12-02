package com.wot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WordotechApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordotechApplication.class, args);
        System.out.println("Wordotech Started Successfully");

        

    }


}

