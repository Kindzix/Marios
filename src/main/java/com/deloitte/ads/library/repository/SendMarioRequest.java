package com.deloitte.ads.library.repository;
import java.util.Set;
public class SendMarioRequest {

    private Long marioId;
    private String comment;
    private String senderEmail;
    private Set<String> recipientEmails;

    public Long getMarioId() {
        return marioId;
    }

    public void setMarioId(Long marioId) {
        this.marioId = marioId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Set<String> getRecipientEmails() {
        return recipientEmails;
    }

    public void setRecipientEmails(Set<String> recipientEmails) {
        this.recipientEmails = recipientEmails;
    }

    public SendMarioRequest(Long marioId, String comment, String senderEmail, Set<String> recipientEmails) {
        this.marioId = marioId;
        this.comment = comment;
        this.senderEmail = senderEmail;
        this.recipientEmails = recipientEmails;
    }
}
