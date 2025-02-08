package com.unrise.webapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticipationList implements Iterable<Participation>, ISection<List<Participation>> {
    private final List<Participation> participationList;

    public ParticipationList() {
        participationList = new ArrayList<>();
    }

    @Override
    public Iterator<Participation> iterator() {
        return participationList.iterator();
    }

    public ParticipationList add(Participation party) {
        participationList.add(party);
        return this;
    }

    public List<Participation> get() {
        return participationList;
    }
}
