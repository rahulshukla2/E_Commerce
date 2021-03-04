package com.example.e_comm;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String title;
    private String price;
    private String category;
    private String quantity;
    private String desc;
    byte[] image;

    public Product(int id, String title, String price, String category, String quantity, String desc, byte[] image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.desc = desc;
        this.image = image;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
