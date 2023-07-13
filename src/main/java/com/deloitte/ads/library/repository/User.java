package com.deloitte.ads.library.repository;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String idUser;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String, String> receivedMario;

    public User(String idUser, String firstName, String lastName, String email) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.receivedMario = new HashMap<>();
    }

    public void addReceivedMario(String marioType, String comment) {
        receivedMario.put(marioType, comment);
    }

    public Map<String, String> getReceivedMario() {
        return receivedMario;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

}
