package com.example.e_comm;

public class User {

    int id;
    String mobile_no;
    String type;
    String name;



    public User(int id, String mobile_no, String type, String name) {
        this.id = id;
        this.mobile_no = mobile_no;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type =type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
