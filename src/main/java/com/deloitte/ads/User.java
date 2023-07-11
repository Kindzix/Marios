package com.deloitte.ads;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String, String> receivedMario;

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.receivedMario = new HashMap<>();
    }

    public void addMario(String marioType, String comment) {
        receivedMario.put(marioType, comment);
    }
    public Map<String, String> getReceivedMario() {
        return receivedMario;
    }
    public String getId() {
        return id;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getEmail() {
        return email;
    }
}


