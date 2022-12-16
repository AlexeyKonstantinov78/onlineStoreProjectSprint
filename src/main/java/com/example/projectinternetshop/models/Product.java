package com.example.projectinternetshop.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;

    @Column(name = "price")
    @Min(value = 1, message = "цена не может быть отрицательной или нулевым")
    @NotNull(message = "Цена не может быть пустым")
    private float price;

    @Column(name = "warehouse", nullable = false)
    @NotEmpty(message = "Склад не может быть пустым")
    private String warehouse;

    @Column(name = "seller", nullable = false)
    @NotEmpty(message = "Продовец не может быть пустым")
    private String seller;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList  = new ArrayList<>();

    @NotNull(message = "Вы не выбрали категорию!!!")
    @ManyToOne(optional = false)
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    private LocalDateTime dateTimeOfCreated;


    public Product() {
    }

    public Product(String title, String description, float price, String warehouse, String seller, Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.warehouse = warehouse;
        this.seller = seller;
        this.category  = category;
    }

    // дата заполнится при создании объекта класса
    @PrePersist
    private void init() {
        dateTimeOfCreated = LocalDateTime.now();
    }

    public void addImageProduct(Image image) {
        System.out.println("вызвана запись в лист " + image.toString() + " " + this);
        image.setProduct(this);
        imageList.add(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public LocalDateTime getDateTimeOfCreated() {
        return dateTimeOfCreated;
    }

    public void setDateTimeOfCreated(LocalDateTime dateTimeOfCreated) {
        this.dateTimeOfCreated = dateTimeOfCreated;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
