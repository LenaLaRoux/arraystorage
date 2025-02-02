package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    Resume[] getAllSorted();

    int size();
}
