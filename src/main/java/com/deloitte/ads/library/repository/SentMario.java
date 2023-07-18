package com.deloitte.ads.library.repository;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "SENT_MARIO")
public class SentMario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MARIO_ID")
    private Mario mario;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @ManyToMany
    @JoinTable(
            name = "SENT_MARIO_RECIPIENTS",
            joinColumns = @JoinColumn(name = "SENT_MARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPIENT_ID")
    )
    private Set<User> recipients;

    public SentMario() {}

    public SentMario(Mario mario, String comment, User sender, Set<User> recipients) {
        this.mario = mario;
        this.comment = comment;
        this.sender = sender;
        this.recipients = recipients;
    }

    public Long getId() {
        return id;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<User> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "SentMario{" +
                "id=" + id +
                ", mario=" + mario +
                ", comment='" + comment + '\'' +
                ", sender=" + sender +
                ", recipients=" + recipients +
                '}';
    }
}
