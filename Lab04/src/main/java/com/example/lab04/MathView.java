package com.example.lab04;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Route(value = "index1")
public class MathView extends VerticalLayout {

    private NumberField ans;

    public MathView() {
        NumberField n1 = new NumberField();
        n1.setLabel("Number 1");
        n1.setWidthFull();

        NumberField n2 = new NumberField();
        n2.setLabel("Number 2");
        n2.setWidthFull();

        HorizontalLayout btnContainer = new HorizontalLayout();
        btnContainer.setWidthFull();
        Label btnLabel = new Label();
        btnLabel.setText("Operator");
        btnLabel.getStyle().set("font-size", "20").set("font-weight", "bold");

        Button plusBtn = new Button();
        plusBtn.setText("+");
        plusBtn.addClickListener(e -> {
            double num1 = n1.getValue();
            double num2 = n2.getValue();

            double res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/plus/" + num1 + "/" + num2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        Button minusBtn = new Button();
        minusBtn.setText("-");
        minusBtn.addClickListener(e -> {
            double num1 = n1.getValue();
            double num2 = n2.getValue();

            double res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/minus/" + num1 + "/" + num2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        Button multiBtn = new Button();
        multiBtn.setText("x");
        multiBtn.addClickListener(e -> {
            double num1 = n1.getValue();
            double num2 = n2.getValue();

            double res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/multi/" + num1 + "/" + num2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        Button divideBtn = new Button();
        divideBtn.setText("/");
        divideBtn.addClickListener(e -> {
            double num1 = n1.getValue();
            double num2 = n2.getValue();

            double res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/divide/" + num1 + "/" + num2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        Button modBtn = new Button();
        modBtn.setText("Mod");
        modBtn.addClickListener(e -> {
            double num1 = n1.getValue();
            double num2 = n2.getValue();

            double res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/mod/" + num1 + "/" + num2)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        Button maxBtn = new Button();
        maxBtn.setText("Max");
        maxBtn.addClickListener(e -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("n1", n1.getValue() + "");
            formData.add("n2", n2.getValue() + "");

            double res = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/max")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();
            ans.setValue(res);
        });

        btnContainer.add(plusBtn, minusBtn, multiBtn, divideBtn, modBtn, maxBtn);

        ans = new NumberField();
        ans.setLabel("Answer");
        ans.getStyle().set("font-weight", "bold");
        ans.setWidthFull();

        this.add(n1, n2, btnLabel, btnContainer, ans);
        this.setWidth(600, Unit.PIXELS);
        this.getStyle().set("justify-content", "center").set("margin", "auto");
    }

}
