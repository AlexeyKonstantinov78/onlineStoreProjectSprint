package com.example.projectinternetshop.models;

import java.time.LocalDateTime;

public class OrderTitle {
    private String number;

    private LocalDateTime dateTime;

    public OrderTitle(String number, LocalDateTime dateTime) {
        this.number = number;
        this.dateTime = dateTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
