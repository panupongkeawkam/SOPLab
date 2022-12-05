package com.example.lab03;

import org.springframework.web.bind.annotation.*;

@RestController
public class MathService {

    @GetMapping(value = "/add/{num1}/{num2}")
    public double add(@PathVariable("num1") Double num1, @PathVariable("num2") Double num2) {
        return num1 + num2;
    }

    @GetMapping(value = "/minus/{num1}/{num2}")
    public double minus(@PathVariable("num1") Double num1, @PathVariable("num2") Double num2) {
        return num1 - num2;
    }

    @GetMapping(value = "/multiply")
    public double multilpy(@RequestParam("num1") Double num1, @RequestParam("num2") Double num2) {
        return num1 * num2;
    }

    @GetMapping(value = "divide")
    public double divide(@RequestParam("num1") Double num1, @RequestParam("num2") Double num2) {
        return num1 / num2;
    }
}
