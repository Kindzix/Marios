package com.deloitte.ads.library.repository;

import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "mario")
public class Mario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "type")
    private String type;

    public Mario() {
    }

    public Mario(String type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Mario{" +
                "idMarios=" + id +
                ", type='" + type + '\'' +
                '}';
    }

}
