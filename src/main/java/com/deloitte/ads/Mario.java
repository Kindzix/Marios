package com.deloitte.ads;

import java.util.Set;
public class Mario {
    private String id;
    private User sender;
    private Set<User> recipients;
    private String type;

    public Mario(String id, User sender, Set<User> recipients, String type) {
        this.id = id;
        this.sender = sender;
        this.recipients = recipients;
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public User getSender() {
        return sender;
    }
    public Set<User> getRecipients() {
        return recipients;
    }
    public String getType() {
        return type;
    }
}
