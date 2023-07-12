package com.deloitte.ads.library.repository;

import java.util.Set;

public class SentMario {
    private final Mario mario;
    private final String comment;
    private final String senderId;
    private final Set<User> recipients;

    public SentMario(Mario mario, String comment, String senderId, Set<User> recipients) {
        this.mario = mario;
        this.comment = comment;
        this.senderId = senderId;
        this.recipients = recipients;
    }

    public Mario getMario() {
        return mario;
    }

    public String getComment() {
        return comment;
    }

    public String getSenderId() {
        return senderId;
    }

    public Set<User> getRecipients() {
        return recipients;
    }
}
