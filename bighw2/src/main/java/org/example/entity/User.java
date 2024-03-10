package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.mapping.Set;

import java.util.List;

@Entity(name = "person")

public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "userType")
    private String userType; // admin or customer

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<FoodOrder> foodOrders;


    public User(String name, String login, String passwordHash, String userType) {
        this.name = name;
        this.login = login;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    public User() {}

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
