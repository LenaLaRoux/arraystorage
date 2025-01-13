package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final String NOT_IN_DB = "ERROR: Resume is not in database";

    protected static final String IS_IN_DB = "ERROR: Resume is already in database";

    protected static final String NO_SPACE_LEFT = "ERROR: Resume database is full";

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void update(Resume r) {
        if (r == null) {
            return;
        }

        int foundIndex = getIndex(r.getUuid());
        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }
        storage[foundIndex] = r;
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int foundIndex = getIndex(uuid);

        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return null;
        }
        return storage[foundIndex];
    }


    @Override
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
            processSave(r, foundIndex);
        }
    }

    @Override
    public void delete(String uuid) {
        int foundIndex = getIndex(uuid);
        if (foundIndex < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }
        processDelete(uuid, foundIndex);
        storage[--size] = null;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void processDelete(String uuid, int index);

    protected abstract void processSave(Resume resume, int index);
}