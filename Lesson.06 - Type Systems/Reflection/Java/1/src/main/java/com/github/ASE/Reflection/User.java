package com.github.ASE.Reflection;

@Table(name = "users")
public class User {
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
