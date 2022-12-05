package com.example.lab06.repository;

import com.example.lab06.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {

    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository) {
        this.repository = repository;
    }

    public List<Wizard> retrieveWizards() {
        return repository.findAll();
    }

    public Wizard retrieveWizardById(String _id) {
        return repository.findById(_id).get();
    }

    public Wizard retrieveWizardByName(String name) {
        return repository.findByName(name);
    }

    public Wizard createWizard(Wizard wizard) {
        return repository.insert(wizard);
    }

    public Wizard updateWizard(Wizard wizard) {
        return repository.save(wizard);
    }

    public void deleteWizard(Wizard wizard) {
        repository.delete(wizard);
    }

}
