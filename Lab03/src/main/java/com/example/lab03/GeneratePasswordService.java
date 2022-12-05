package com.example.lab03;

import org.springframework.web.bind.annotation.*;

@RestController
public class GeneratePasswordService {

    @GetMapping(value = "/{name}.generate")
    public String generate(@PathVariable("name") String name) {
        int password = (int) ((Math.random() * (999999999 - 100000000)) + 100000000);
        return "Hi, " + name + "<br>Your new password is " + password + ".";
    }

}
