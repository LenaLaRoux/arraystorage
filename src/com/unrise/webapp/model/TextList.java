package com.unrise.webapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TextList extends ASection implements Iterable<String> {
    private final List<String> lines;

    public TextList() {
        lines = new ArrayList<>();
    }

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }

    public TextList add(String text) {
        lines.add(text);
        return this;
    }

    public List<String> get() {
        return lines;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextList strings = (TextList) o;
        return Objects.equals(lines, strings.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }

    @Override
    public String toString() {
        return "TextList{" +
                "lines=" + lines +
                '}';
    }
}
