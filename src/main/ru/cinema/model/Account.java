package ru.cinema.model;

public class Account {

    private int id;
    private String userName;
    private String email;
    private String phone;

    public Account(int id, String userName, String email, String phone) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }
}

