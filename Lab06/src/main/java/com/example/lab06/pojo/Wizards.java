package com.example.lab06.pojo;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

public class Wizards extends ArrayList<Wizard> {

    public Wizards() {

        super(WebClient
                .create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block());
    }
}
