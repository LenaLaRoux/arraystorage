package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    final private String NOT_IN_DB = "ERROR: Resume is not in database";
    final private String IS_IN_DB = "ERROR: Resume is already in database";
    final private String NO_SPACE_LEFT = "ERROR: Resume database is full";
    Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r == null) return;

        int foundIdx = search(r.getUuid());

        if (foundIdx >= 0) {
            System.out.println(IS_IN_DB);
            return;
        }

        if (size > storage.length) {
            System.out.println(NO_SPACE_LEFT);
            return;
        }

        storage[size++] = r;
    }

    public Resume get(String uuid) {
        int foundIdx = search(uuid);

        if (foundIdx < 0) {
            System.out.println(NOT_IN_DB);
            return null;
        }

        return storage[foundIdx];
    }

    public void delete(String uuid) {
        int foundIdx = search(uuid);
        if (foundIdx < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }

        final int endIdx = size - 1;

        if (foundIdx == endIdx)
            storage[foundIdx] = null;
        else {
            storage[foundIdx] = storage[endIdx];
            storage[endIdx] = null;
        }
        size--;
    }

    public void update(Resume r) {
        if (r == null) return;

        int foundIdx = search(r.getUuid());

        if (foundIdx < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }

        storage[foundIdx] = r;
    }

    protected int search(String uuid) {

        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) return i;
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
