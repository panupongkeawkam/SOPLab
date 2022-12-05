package com.example.lab04;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cashier {

    @GetMapping("getChange/{change}")
    public Change getChange(@PathVariable("change") int change) {

        int b1000 = (int) Math.floor(change / 1000);
        change %= 1000;
        int b500 = (int) Math.floor(change / 500);
        change %= 500;
        int b100 = (int) Math.floor(change / 100);
        change %= 100;
        int b20 = (int) Math.floor(change / 20);
        change %= 20;;
        int b10 = (int) Math.floor(change / 10);
        change %= 10;
        int b5 = (int) Math.floor(change / 5);
        change %= 5;

        return new Change(b1000, b500, b100, b20, b10, b5, change);
    }
}
