package com.deloitte.ads.library.repository;


import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "sent_mario")
public class SentMario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "mario_id")
    private Mario mario;

    @Column(name = "theme")
    private String theme;

    @Column(name = "comment")
    private String comment;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "sent_mario_recipients",
            joinColumns = @JoinColumn(name = "sent_mario_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private Set<User> recipients;

    public SentMario() {
    }

    public SentMario(Mario mario, String comment, User sender, Set<User> recipients, String theme) {
        this.mario = mario;
        this.comment = comment;
        this.theme = theme;
        this.sender = sender;
        this.recipients = recipients;
        this.uuid = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public String getRecipientNames() {
        if (recipients != null && !recipients.isEmpty()) {
            List<String> names = new ArrayList<>();
            for (User recipient : recipients) {
                String fullName = recipient.getFirstName() + " " + recipient.getLastName();
                names.add(fullName);
            }
            return String.join(", ", names);
        }
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
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
