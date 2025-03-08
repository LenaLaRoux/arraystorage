package com.unrise.webapp.model;

import java.io.Serial;
import java.util.Objects;


public class ListSection extends ASection {
    @Serial
    private static final long serialVersionUID = 1L;

    private String value;

    public ListSection(String text) {
        value = text;
    }

    @Override
    public String get() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "value='" + value + '\'' +
                '}';
    }
}
