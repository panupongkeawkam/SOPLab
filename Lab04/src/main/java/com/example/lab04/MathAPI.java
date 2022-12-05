package com.example.lab04;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class MathAPI {

    @GetMapping("/plus/{n1}/{n2}")
    public double myPlus(@PathVariable("n1") double n1, @PathVariable("n2") double n2) {
        return n1 + n2;
    }

    @GetMapping("/minus/{n1}/{n2}")
    public double myMinus(@PathVariable("n1") double n1, @PathVariable("n2") double n2) {
        return n1 - n2;
    }

    @GetMapping("/divide/{n1}/{n2}")
    public double myDivide(@PathVariable("n1") double n1, @PathVariable("n2") double n2) {
        return n1 / n2;
    }

    @GetMapping("/multi/{n1}/{n2}")
    public double myMulti(@PathVariable("n1") double n1, @PathVariable("n2") double n2) {
        return n1 * n2;
    }

    @GetMapping("/mod/{n1}/{n2}")
    public double myMod(@PathVariable("n1") double n1, @PathVariable("n2") double n2) {
        return n1 % n2;
    }

    @PostMapping("/max")
    public double myMax(@RequestBody MultiValueMap<String, String> body) {
        Map<String, String> data = body.toSingleValueMap();
        double maxValue = Math.max(Double.parseDouble(data.get("n1")), Double.parseDouble(data.get("n2")));
        return maxValue;
    }
}
