package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage  extends AbstractArrayStorage{

    public void save(Resume r) {
        if (r == null) {
            return;
        }

        int foundIndex = getIndex(r.getUuid());

        if (size > storage.length) {
            System.out.println(NO_SPACE_LEFT);
        } else if (foundIndex >= 0) {
            System.out.println(IS_IN_DB);
        } else {
            storage[size++] = r;
        }
    }

    public void delete(String uuid) {
        int foundIndex = getIndex(uuid);
        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }

        size--;
        storage[foundIndex] = storage[size];
        storage[size] = null;
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
