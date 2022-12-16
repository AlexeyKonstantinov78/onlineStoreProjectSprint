package com.example.projectinternetshop.models;

import java.time.LocalDate;

public class OrdersTitle {

    private int person_id;

    private Person person;

    private String number;

    private LocalDate dateTime;
    public OrdersTitle(int person_id, Person person, String number, LocalDate dateTime) {
        this.person_id = person_id;
        this.person = person;
        this.number = number;
        this.dateTime = dateTime;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }


}
