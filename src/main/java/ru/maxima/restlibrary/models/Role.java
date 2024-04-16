package ru.maxima.restlibrary.models;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
