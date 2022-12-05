package com.example.lab03;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    private List<Customer> customers;

    public CustomerController() {
        this.customers = new ArrayList<Customer>();
        this.customers.add(new Customer("1010", "John", true, 25));
        this.customers.add(new Customer("1018", "Peter", true, 24));
        this.customers.add(new Customer("1019", "Sara", false, 23));
        this.customers.add(new Customer("1110", "Rose", false, 23));
        this.customers.add(new Customer("1001", "Emma", false, 30));
    }

    @GetMapping(value = "/customers")
    public List<Customer> getCustomers() {
        return this.customers;
    }

    @GetMapping(value = "/customerbyid/{id}")
    public Customer getCustomerByID(@PathVariable("id") String ID) {
        Customer customerTarget = null;
        for (Customer customer : this.customers) {
            if (customer.getID().equals(ID)) {
                customerTarget = customer;
                break;
            }
        }
        return customerTarget;
    }


    @GetMapping(value = "/customerbyname/{name}")
    public Customer getCustomerByName(@PathVariable("name") String name) {
        Customer customerTarget = null;
        for (Customer customer : this.customers) {
            if (customer.getName().equals(name)) {
                customerTarget = customer;
                break;
            }
        }
        return customerTarget;
    }

    @DeleteMapping(value = "/customerDelByid/{id}")
    public boolean delCustomerByID(@PathVariable("id") String ID) {
        boolean deleted = false;
        for (Customer customer : this.customers) {
            if (customer.getID().equals(ID)) {
                this.customers.remove(customer);
                deleted = true;
                break;
            }
        }
        return deleted;
    }

    @DeleteMapping(value = "/customerDelByname/{name}")
    public boolean delCustomerByName(@PathVariable("name") String name) {
        boolean deleted = false;
        for (Customer customer : this.customers) {
            if (customer.getName().equals(name)) {
                this.customers.remove(customer);
                deleted = true;
                break;
            }
        }
        return deleted;
    }

    @GetMapping(value = "/addCustomer")
    public boolean addCustomer(@RequestParam("id") String ID, @RequestParam("name") String name, @RequestParam("sex") String sex, @RequestParam("age") int age) {
        try {
            boolean sexBool = true;
            if (sex.equals("male") || sex.equals("Male")) {
                sexBool = true;
            } else if (sex.equals("female") || sex.equals("Female")) {
                sexBool = false;
            }

            Customer customer = new Customer(ID, name, sexBool, age < 0 ? 0 : age);
            this.customers.add(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping(value = "/addCustomer2")
    public boolean addCustomer2(@RequestParam("id") String ID, @RequestParam("name") String name, @RequestParam("sex") String sex, @RequestParam("age") int age) {
        try {
            boolean sexBool = true;
            if (sex.equals("male") || sex.equals("Male")) {
                sexBool = true;
            } else if (sex.equals("female") || sex.equals("Female")) {
                sexBool = false;
            }

            Customer customer = new Customer(ID, name, sexBool, age < 0 ? 0 : age);
            this.customers.add(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
