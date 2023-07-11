package com.deloitte.ads.library.repository;

import java.util.List;
import java.util.Set;
public class Mario {
    private final String idMarios;
    private final String type;

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
