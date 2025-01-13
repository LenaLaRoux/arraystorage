package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void processSave(Resume resume, int index) {
        storage[size++] = resume;
    }

    @Override
    protected void processDelete(String uuid, int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1; //not found
    }
}
