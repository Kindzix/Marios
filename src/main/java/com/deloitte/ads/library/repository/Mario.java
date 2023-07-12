package com.deloitte.ads.library.repository;

public class Mario {
    private String idMarios;
    private String type;

    public Mario(String idMarios, String type) {
        this.idMarios = idMarios;
        this.type = type;
    }

    public String getIdMarios() {
        return idMarios;
    }
    public String getType() {
        return type;
    }
}
