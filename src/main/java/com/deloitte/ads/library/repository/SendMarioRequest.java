package com.deloitte.ads.library.repository;

import java.util.Set;
import java.util.UUID;

public class SendMarioRequest {
    private String marioUuid;
    private String comment;
    private String theme;
    private String senderUuid;
    private Set<String> recipientUuids;

    public String getMarioUuid() {
        return marioUuid;
    }

    public void setMarioUuid(String marioUuid) {
        this.marioUuid = marioUuid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(String senderUuid) {
        this.senderUuid = senderUuid;
    }

    public Set<String> getRecipientUuids() {
        return recipientUuids;
    }

    public void setRecipientUuids(Set<String> recipientUuids) {
        this.recipientUuids = recipientUuids;
    }

    public SentMario toSentMario(Mario mario, User sender, Set<User> recipients, String comment, String theme) {
        SentMario sentMario = new SentMario();
        sentMario.setMario(mario);
        sentMario.setComment(comment);
        sentMario.setTheme(theme);
        sentMario.setSender(sender);
        sentMario.setRecipients(recipients);
        sentMario.setUuid(UUID.randomUUID());
        return sentMario;
    }
}

