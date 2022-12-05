package com.example.lab05;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    protected Sentence sentence;

    public SentenceConsumer() {
        sentence = new Sentence();
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s) {
        this.sentence.badSentences.add(s);
        System.out.print("In addBadSentence Method: [");
        for (int i = 0; i < this.sentence.badSentences.size(); i++) {
            System.out.print(this.sentence.badSentences.get(i));
            if (i == this.sentence.badSentences.size() - 1) {
                break;
            }
            System.out.print(", ");
        }
        System.out.println("]");
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s) {
        this.sentence.goodSentences.add(s);
        System.out.print("In addGoodSentence Method: [");
        for (int i = 0; i < this.sentence.goodSentences.size(); i++) {
            System.out.print(this.sentence.goodSentences.get(i));
            if (i == this.sentence.goodSentences.size() - 1) {
                break;
            }
            System.out.print(", ");
        }
        System.out.println("]");
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentence() {
        return this.sentence;
    }
}
