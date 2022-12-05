package com.example.readingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/to-read")
public class ReadingController {

    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    @GetMapping
    public List<Object> toRead() {
        return circuitBreakerFactory.create("recommeded").run(
                () -> new RestTemplate().getForObject("http://localhost:8090/recommended", List.class),
                throwable -> {
                    List<Object> listBuffer = new ArrayList<Object>();
                    listBuffer.add("Spring in Action (Manning)");

                    return listBuffer;
                }
        );
    }

}
