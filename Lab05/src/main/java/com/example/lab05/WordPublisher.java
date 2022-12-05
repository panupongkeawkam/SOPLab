package com.example.lab05;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words;

    public WordPublisher() {
        words = new Word();
    }

    @GetMapping("/getBad") // alternate
    public ArrayList<String> getBadWords() {
        return this.words.badWords;
    }

    @PostMapping("/addBad/{s}")
    public ArrayList<String> addBadWord(@PathVariable("s") String s) {
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @DeleteMapping("/delBad/{s}")
    public ArrayList<String> deleteBadWord(@PathVariable("s") String s) {
        this.words.badWords.remove(s);
        return this.words.badWords;
    }

    @GetMapping("/getGood") // alternate
    public ArrayList<String> getGoodWords() {
        return this.words.goodWords;
    }

    @PostMapping("/addGood/{s}")
    public ArrayList<String> addGoodWord(@PathVariable("s") String s) {
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }

    @DeleteMapping("/delGood/{s}")
    public ArrayList<String> deleteGoodWord(@PathVariable("s") String s) {
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @PostMapping("/proof/{sentence}")
    public String proofSentence(@PathVariable("sentence") String s) {
        boolean hasBad = false;
        boolean hasGood = false;
        String msg = "";

        for (Object badWord : this.words.badWords.toArray()) {
            if (s.toLowerCase().contains(((String) badWord).toLowerCase())) {
                hasBad = true;
                break;
            }
        }

        for (Object goodWord : this.words.goodWords.toArray()) {
            if (s.toLowerCase().contains(((String) goodWord).toLowerCase())) {
                hasGood = true;
                break;
            }
        }

        if (hasGood && hasBad) {
            rabbitTemplate.convertAndSend("FanoutExchange", "", s);
            msg = "Found Bad & Good Word";
        } else if (hasGood) {
            rabbitTemplate.convertAndSend("DirectExchange", "good", s);
            msg = "Found Good Word";
        } else if (hasBad) {
            rabbitTemplate.convertAndSend("DirectExchange", "bad", s);
            msg = "Found Bad Word";
        }

        return msg;
    }

    @GetMapping("/getSentence")
    public Sentence getSentence() {
        Object res = rabbitTemplate.convertSendAndReceive("GetQueue", "getsentence");
        return (Sentence) res;
    }

}
