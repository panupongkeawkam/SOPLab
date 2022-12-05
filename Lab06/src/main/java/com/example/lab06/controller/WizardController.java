package com.example.lab06.controller;

import com.example.lab06.pojo.Wizard;
import com.example.lab06.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @GetMapping("/wizards")
    public List<Wizard> getWizards() {
        System.out.println("/wizards called");
        List<Wizard> wizards = wizardService.retrieveWizards();
        return wizards;
    }

    @PostMapping("/addWizard")
    public ResponseEntity<?> addWizard(@RequestBody MultiValueMap<String, String> body) {
        System.out.println("/addWizard called");

        Map<String, String> data = body.toSingleValueMap();
        Wizard wizard = wizardService.createWizard(
                new Wizard(
                        null,
                        data.get("sex"),
                        data.get("name"),
                        data.get("school"),
                        data.get("house"),
                        Double.parseDouble(data.get("money")),
                        data.get("position")
                )
        );
        return ResponseEntity.ok(wizard);
    }

    @PostMapping("/updateWizard")
    public ResponseEntity<?> updateWizard(@RequestBody MultiValueMap<String, String> body) {
        System.out.println("/updateWizard called");

        Map<String, String> data = body.toSingleValueMap();
        Wizard existsWizard = wizardService.retrieveWizardById(data.get("_id"));
        if (existsWizard != null) {
            Wizard wizard = wizardService.updateWizard(
                    new Wizard(
                            data.get("_id"),
                            data.get("sex"),
                            data.get("name"),
                            data.get("school"),
                            data.get("house"),
                            Double.parseDouble(data.get("money")),
                            data.get("position")
                    )
            );
            return ResponseEntity.ok(wizard);
        } else {
            return null;
        }
    }

    @PostMapping("/deleteWizard")
    public ResponseEntity<?> deleteWizard(@RequestBody MultiValueMap<String, String> body) {
        System.out.println("/deleteWizard called");

        Map<String, String> data = body.toSingleValueMap();
        Wizard existsWizard = wizardService.retrieveWizardById(data.get("_id"));
        if (existsWizard != null) {
            wizardService.deleteWizard(existsWizard);
            return ResponseEntity.ok(existsWizard);
        } else {
            return null;
        }
    }
}
