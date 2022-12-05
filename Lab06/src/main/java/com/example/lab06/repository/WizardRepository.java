package com.example.lab06.repository;

import com.example.lab06.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {

    @Query(value = "{name:  '?0'}")
    public Wizard findByName(String name);

}
