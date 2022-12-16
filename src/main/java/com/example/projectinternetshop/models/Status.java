package com.example.projectinternetshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Статус не может быть пустым")
    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "status")
    private List<Order> orderList;

    public Status() {
    }

    public Status(String status, List<Order> orderList) {
        this.status = status;
        this.orderList = orderList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
