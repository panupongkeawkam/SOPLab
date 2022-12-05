package com.example.bookstoreservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/recommended")
public class BookStoreController {

    @GetMapping
    public List<Object> getBooks() {
        List<Object> list = new ArrayList<Object>();
        list.add("Spring in Action (Manning)");
        list.add("Cloud Native Java (O'Reilly)");
        list.add("Learning Spring Boot (Packt)");

        return list;
    }

}
