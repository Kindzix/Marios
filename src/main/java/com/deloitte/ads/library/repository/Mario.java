package com.deloitte.ads.library.repository;

import javax.persistence.*;

@Entity(name="MARIO")
public class Mario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long idMarios;

    @Column(name = "TYPE")
    private String type;

    public Mario() {}

    public Mario(String type) {
        this.type = type;
    }

    public Long getIdMarios() {
        return idMarios;
    }

    public void setIdMarios(Long idMarios) {
        this.idMarios = idMarios;
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
                "idMarios=" + idMarios +
                ", type='" + type + '\'' +
                '}';
    }

}
