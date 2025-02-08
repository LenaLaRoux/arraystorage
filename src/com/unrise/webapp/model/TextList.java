package com.unrise.webapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextList implements Iterable<String>, ISection<List<String>> {
    private final List<String> textList;

    public TextList() {
        textList = new ArrayList<>();
    }

    @Override
    public Iterator<String> iterator() {
        return textList.iterator();
    }

    public TextList add(String text) {
        textList.add(text);
        return this;
    }

    public List<String> get() {
        return textList;
    }
}
