package com.example.lab05;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route("index")
public class View extends FormLayout {

    private TextField addWordInput, addSentenceInput;
    private TextArea goodSentenceOutput, badSentenceOutput;
    private Button addGoodBtn, addBadBtn, addSentenceBtn, showSentenceBtn;
    private ComboBox<String> goodWordsList, badWordsList;


    public View() {
        FormLayout leftContainer = new FormLayout();
        FormLayout rightContainer = new FormLayout();

        addWordInput = new TextField("Add Word");
        addSentenceInput = new TextField("Add Sentence");

        goodSentenceOutput = new TextArea("Good Sentences");
        badSentenceOutput = new TextArea("Bad Sentences");
        badSentenceOutput.setHeight(120, Unit.PIXELS);

        addGoodBtn = new Button("Add Good Word");
        addGoodBtn.addClickListener(e -> {
            ArrayList res = WebClient
                    .create()
                    .post()
                    .uri("http://localhost:8080/addGood/" + addWordInput.getValue())
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWordsList.setItems(res);
            Notification notification = Notification.show("Insert " + addWordInput.getValue() + " to Good Words Lists Complete.");
            notification.setPosition(Notification.Position.BOTTOM_START);
            addWordInput.setValue("");
        });

        addBadBtn = new Button("Add Bad Word");
        addBadBtn.getStyle().set("margin", "1rem");
        addBadBtn.addClickListener(e -> {
            ArrayList res = WebClient
                    .create()
                    .post()
                    .uri("http://localhost:8080/addBad/" + addWordInput.getValue())
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badWordsList.setItems(res);
            Notification notification = Notification.show("Insert " + addWordInput.getValue() + " to Bad Words Lists Complete.");
            notification.setPosition(Notification.Position.BOTTOM_START);
            addWordInput.setValue("");
        });

        addSentenceBtn = new Button("Add Sentence");
        addSentenceBtn.addClickListener(e -> {
            String res = WebClient
                    .create()
                    .post()
                    .uri("http://localhost:8080/proof/" + addSentenceInput.getValue())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show(res);
            notification.setPosition(Notification.Position.BOTTOM_START);
            addSentenceInput.setValue("");
        });

        showSentenceBtn = new Button("Show Sentence");
        showSentenceBtn.getStyle().set("margin", "1rem");
        showSentenceBtn.addClickListener(e -> {
            Sentence res = WebClient
                    .create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();

            String text1 = "";
            for (int i = 0; i < res.goodSentences.size(); i++) {
                text1 += res.goodSentences.get(i);
                if (i == res.goodSentences.size() - 1) {
                    goodSentenceOutput.setValue(text1);
                    break;
                }
                text1 += ", ";
            }

            String text2 = "";
            for (int i = 0; i < res.badSentences.size(); i++) {
                text2 += res.badSentences.get(i);
                if (i == res.badSentences.size() - 1) {
                    badSentenceOutput.setValue(text2);
                    break;
                }
                text2 += ", ";
            }
        });

        goodWordsList = new ComboBox<String>("Good Words");
        ArrayList goodWords = WebClient
                .create()
                .get()
                .uri("http://localhost:8080/getGood")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block();
        goodWordsList.setItems(goodWords);

        badWordsList = new ComboBox<String>("Bad Words");
        ArrayList badWords = WebClient
                .create()
                .get()
                .uri("http://localhost:8080/getBad")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block();
        badWordsList.setItems(badWords);

        leftContainer.add(addWordInput, addGoodBtn, addBadBtn, goodWordsList, badWordsList);
        rightContainer.add(addSentenceInput, addSentenceBtn, goodSentenceOutput, badSentenceOutput, showSentenceBtn);

        this.add(leftContainer, rightContainer);
        this.getStyle()
                .set("width", "1080px")
                .set("margin", "auto")
                .set("box-sizing", "border-box")
                .set("padding", "2em");
    }

}
