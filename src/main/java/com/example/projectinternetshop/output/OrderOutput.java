package com.example.projectinternetshop.output;

import java.time.LocalDateTime;

public class OrderOutput {
    private int id;
    private String number;
    private int count;
    private float price;
    private LocalDateTime dateTime;
    private String status;
    private String productTitle;
    private String nameUser;

    public OrderOutput(int id, String number, int count, float price, LocalDateTime dateTime, String status, String productTitle, String nameUser) {
        this.id = id;
        this.number = number;
        this.count = count;
        this.price = price;
        this.dateTime = dateTime;
        this.status = status;
        this.productTitle = productTitle;
        this.nameUser = nameUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
