package com.example.applicationkt.model;

import java.util.List;

public class Cart {
    private Long id;
    private Users users;
    private List<CartItem> items;

    public Cart() {}

    public Cart(Long id, Users users, List<CartItem> items) {
        this.id = id;
        this.users = users;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
