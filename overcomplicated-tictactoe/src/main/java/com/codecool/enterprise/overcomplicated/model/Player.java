package com.codecool.enterprise.overcomplicated.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URI;

@Entity
@Component
public class Player {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    String userName = "Anonymous";

    public String getUserName() {
        return userName;
    }

    public Integer getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
