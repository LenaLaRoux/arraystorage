package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final String NOT_IN_DB = "ERROR: Resume is not in database";

    private static final String IS_IN_DB = "ERROR: Resume is already in database";

    private static final String NO_SPACE_LEFT = "ERROR: Resume database is full";

    private static final int MAX_SIZE = 10000;

    protected final Resume[] storage = new Resume[MAX_SIZE];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r == null) {
            return;
        }

        int foundIndex = findIndex(r.getUuid());

        if (size > storage.length) {
            System.out.println(NO_SPACE_LEFT);
        } else if (foundIndex >= 0) {
            System.out.println(IS_IN_DB);
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int foundIndex = findIndex(uuid);

        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return null;
        }
        return storage[foundIndex];
    }

    public void delete(String uuid) {
        int foundIndex = findIndex(uuid);
        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }

        size--;
        storage[foundIndex] = storage[size];
        storage[size] = null;
    }

    public void update(Resume r) {
        if (r == null) {
            return;
        }

        int foundIndex = findIndex(r.getUuid());
        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }
        storage[foundIndex] = r;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1; //not found
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
