package com.unrise.webapp.model;

public class Text implements ISection<String> {
    private final String value;

    public Text(String text) {
        value = text;
    }

    @Override
    public String get() {
        return value;
    }
}
