package com.example.demo.entities;
public enum NetworkType {
    G5("5G"),
    G4("4G"),
    G3("3G"),
    WIFI,
    IOT,
    ETHERNET;

    private final String label;

    NetworkType() {
        this.label = name(); // defaults to enum name if no label needed
    }

    NetworkType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
