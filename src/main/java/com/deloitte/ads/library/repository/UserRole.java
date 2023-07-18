package com.deloitte.ads.library.repository;

public enum UserRole {
    ADMIN("Administrator"),
    USER("User");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
