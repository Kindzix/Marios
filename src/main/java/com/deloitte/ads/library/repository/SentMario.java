package com.deloitte.ads.library.repository;
public class SentMario {
    private final Mario marios;
    private final String comment;
    private final String senderId;
    private final String recipientId;

    public SentMario(Mario marios, String comment, String senderId, String recipientId) {
        this.marios = marios;
        this.comment = comment;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public Mario getMario() {
        return marios;
    }

    public String getComment() {
        return comment;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }
}
