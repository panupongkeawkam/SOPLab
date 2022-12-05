package com.example.lab03;

import org.springframework.web.bind.annotation.*;

@RestController
public class Customer {

    private String ID;
    private String name;
    private boolean sex;
    private int age;

    public Customer(String ID, String name, boolean sex, int age) {
        this.ID = ID;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Customer() {
        this.ID = "";
        this.name = null;
        this.sex = false;
        this.age = 0;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean isSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
