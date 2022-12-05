package com.example.lab04;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

@Route("index2")
public class CashierView extends VerticalLayout {

    private NumberField changeField;
    private NumberField b1000Field;
    private NumberField b500Field;
    private NumberField b100Field;
    private NumberField b20Field;
    private NumberField b10Field;
    private NumberField b5Field;
    private NumberField b1Field;
    private Button btn;

    public CashierView() {
        changeField = new NumberField();
        changeField.setLabel("เงินทอน");
        changeField.setPrefixComponent(new Div(new Text("$")));
        changeField.setWidth(60, Unit.PERCENTAGE);

        btn = new Button("คำนวณเงินทอน");
        btn.setWidth(60, Unit.PERCENTAGE);
        btn.getStyle()
                .set("background-color", "#2098d1")
                .set("color", "white");
        btn.addClickListener(e -> {
            Change res = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getChange/" + (int) Math.floor(changeField.getValue()))
                    .retrieve()
                    .bodyToMono(Change.class)
                    .block();

            Text resultText[] = {
                    new Text(res.getB1000() + ""),
                    new Text(res.getB500() + ""),
                    new Text(res.getB100() + ""),
                    new Text(res.getB20() + ""),
                    new Text(res.getB10() + ""),
                    new Text(res.getB5() + ""),
                    new Text(res.getB1() + "")
            };

            // loop for shorter code
            Div div[] = new Div[7];
            for (int i = 0; i < 7; i++) {
                div[i] = new Div(resultText[i]);
                div[i].getStyle().set("color", "#2ca444");
            }

            // set result in each field
            b1000Field.setSuffixComponent(div[0]);
            b500Field.setSuffixComponent(div[1]);
            b100Field.setSuffixComponent(div[2]);
            b20Field.setSuffixComponent(div[3]);
            b10Field.setSuffixComponent(div[4]);
            b5Field.setSuffixComponent(div[5]);
            b1Field.setSuffixComponent(div[6]);
        });

        HorizontalLayout bContainer = new HorizontalLayout();
        bContainer.setWidthFull();
        bContainer.getStyle().set("align-items", "center");

        b1000Field = new NumberField();
        b1000Field.setPrefixComponent(new Div(new Text("$1000")));
        b1000Field.setWidth(60, Unit.PERCENTAGE);

        b500Field = new NumberField();
        b500Field.setPrefixComponent(new Div(new Text("$500")));
        b500Field.setWidth(60, Unit.PERCENTAGE);

        b100Field = new NumberField();
        b100Field.setPrefixComponent(new Div(new Text("$100")));
        b100Field.setWidth(60, Unit.PERCENTAGE);

        b20Field = new NumberField();
        b20Field.setPrefixComponent(new Div(new Text("$20")));
        b20Field.setWidth(60, Unit.PERCENTAGE);

        b10Field = new NumberField();
        b10Field.setPrefixComponent(new Div(new Text("$10")));
        b10Field.setWidth(60, Unit.PERCENTAGE);

        b5Field = new NumberField();
        b5Field.setPrefixComponent(new Div(new Text("$5")));
        b5Field.setWidth(60, Unit.PERCENTAGE);

        b1Field = new NumberField();
        b1Field.setPrefixComponent(new Div(new Text("$1")));
        b1Field.setWidth(60, Unit.PERCENTAGE);

        this.add(changeField, btn, b1000Field, b500Field, b100Field, b20Field, b10Field, b5Field, b1Field);
        this.setWidth(600, Unit.PIXELS);
        this.getStyle()
                .set("margin", "auto")
                .set("display", "flex")
                .set("align-items", "center");
    }

}
