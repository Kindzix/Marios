package com.deloitte.ads;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Map<String, String> receivedMario;

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


