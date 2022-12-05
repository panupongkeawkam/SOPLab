package com.example.lab06.view;

import com.example.lab06.pojo.Wizard;
import com.example.lab06.pojo.Wizards;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Route("mainPage.it")
public class MainWizardView extends FormLayout {

    private TextField nameField;
    private RadioButtonGroup<String> genderRadio;
    private ComboBox<String> positionList, schoolList, houseList;
    private NumberField moneyField;
    private Button backBtn, nextBtn, createBtn, updateBtn, delBtn;
    private Label indexLabel;
    private int index;
    private Wizards wizards;

    public MainWizardView() {
        wizards = new Wizards();
        index = wizards.size() == 0 ? -1 : 0;

        HorizontalLayout header = new HorizontalLayout();
        header.getStyle()
                .set("weight", "100%")
                .set("display", "flex")
                .set("justify-content", "flex-end")
                .set("box-sizing", "border-box")
                .set("padding", "1rem");
        indexLabel = new Label();
        indexLabel.getStyle()
                .set("font-size", "24")
                .set("color", "#f80404");
        header.add(indexLabel);

        nameField = new TextField("", "Full name");
        genderRadio = new RadioButtonGroup<String>("Gender:", new String[]{"Male", "Female"});
        genderRadio.setValue("Male");

        positionList = new ComboBox<String>("", new String[]{"Student", "Teacher"});
        positionList.setPlaceholder("Position");

        moneyField = new NumberField("Dollars");
        moneyField.setPrefixComponent(new Div(new Text("$")));

        schoolList = new ComboBox<String>("", new String[]{"Hogwarts", "Beauxbatons", "Durmstrang"});
        schoolList.setPlaceholder("School");

        houseList = new ComboBox<String>("", new String[]{"Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther"});
        houseList.setPlaceholder("House");

        backBtn = new Button("<<");
        backBtn.addClickListener(e -> {
            if (index > 0) {
                index--;
                this.refresh();
            }
        });
        createBtn = new Button("Create");
        createBtn.addClickListener(e -> {
           this.add();
        });
        updateBtn = new Button("Update");
        updateBtn.addClickListener(e -> {
           this.update();
        });
        delBtn = new Button("Delete");
        delBtn.addClickListener(e -> {
           this.delete();
        });
        nextBtn = new Button(">>");
        nextBtn.addClickListener(e -> {
            if (index < wizards.size() - 1) {
                index++;
                this.refresh();
            }
        });

        HorizontalLayout btnContainer = new HorizontalLayout();
        btnContainer.add(backBtn, createBtn, updateBtn, delBtn, nextBtn);
        btnContainer.getStyle()
                .set("width", "100%")
                .set("margin-top", "1rem")
                .set("display", "flex")
                .set("justify-content", "center");

        this.getStyle()
                .set("width", "480px")
                .set("margin", "auto")
                .set("box-sizing", "border-box")
                .set("padding", "1rem");
        this.setResponsiveSteps(
                new ResponsiveStep("480px", 1)
        );
        this.add(header, nameField, genderRadio, positionList, moneyField, schoolList, houseList, btnContainer);
        this.refresh();
    }

    public void add() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", nameField.getValue());
        formData.add("sex", genderRadio.getValue().equals("Male") ? "m" : "f");
        formData.add("school", schoolList.getValue());
        formData.add("house", houseList.getValue());
        formData.add("position", positionList.getValue().toLowerCase());
        formData.add("money", String.valueOf(moneyField.getValue()));

        Wizard res = WebClient.create()
                .post()
                .uri("http://localhost:8080/addWizard")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Wizard.class)
                .block();

        wizards.add(res);
        index = wizards.size() - 1;
        this.refresh();
        Notification.show("Wizard has been created");
    }

    public void update() {
        ObjectMapper mapper = new ObjectMapper();
        Wizard wizard = mapper.convertValue(wizards.get(index), new TypeReference<Wizard>() {});

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("_id", wizard.get_id());
        formData.add("name", nameField.getValue());
        formData.add("sex", genderRadio.getValue().equals("Male") ? "m" : "f");
        formData.add("school", schoolList.getValue());
        formData.add("house", houseList.getValue());
        formData.add("position", positionList.getValue().toLowerCase());
        formData.add("money", String.valueOf(moneyField.getValue()));

        Wizard res = WebClient.create()
                .post()
                .uri("http://localhost:8080/updateWizard")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Wizard.class)
                .block();

        wizards.set(index, res);
        this.refresh();
        Notification.show("Wizard has been updated");
    }

    public void delete() {
        ObjectMapper mapper = new ObjectMapper();
        Wizard wizard = mapper.convertValue(wizards.get(index), new TypeReference<Wizard>() {});

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("_id", wizard.get_id());

        WebClient.create()
                .post()
                .uri("http://localhost:8080/deleteWizard")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .toBodilessEntity()
                .block();

        wizards.remove(index);
        if (index != 0 || wizards.size() == 0) {
            index--;
        }

        if (wizards.size() == 0) {
            nameField.clear();
            genderRadio.setValue("Male");
            positionList.clear();
            moneyField.clear();
            schoolList.clear();
            houseList.clear();
        }

        this.refresh();
        Notification.show("Wizard has been removed");
    }

    public boolean refresh() {
        indexLabel.setText((index >= 0 ? index + 1 : "-") + "");
        backBtn.setEnabled(index > 0);
        nextBtn.setEnabled(index < wizards.size() - 1 && wizards.size() > 1);

        updateBtn.setEnabled(wizards.size() != 0);
        delBtn.setEnabled(wizards.size() != 0);

        if (wizards.size() == 0) {
            index = -1;
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        Wizard wizard = mapper.convertValue(wizards.get(index), new TypeReference<Wizard>() {});

        nameField.setValue(wizard.getName());
        genderRadio.setValue(wizard.getSex().equals("m") ? "Male" : "Female");
        positionList.setValue(this.toCapitalize(wizard.getPosition()));
        moneyField.setValue(wizard.getMoney());
        schoolList.setValue(wizard.getSchool());
        houseList.setValue(wizard.getHouse());

        return true;
    }

    public String toCapitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
    }

}
