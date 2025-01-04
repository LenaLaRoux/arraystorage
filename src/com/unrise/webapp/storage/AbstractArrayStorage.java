package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
            throw new NotExistStorageException(r.getUuid());
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
            throw new NotExistStorageException(uuid);
        }
        return storage[foundIndex];
    }

    @Override
    public void save(Resume r) {
        if (r == null) {
            return;
        }
        if (size >= storage.length) {
            throw new StorageException(NO_SPACE_LEFT, r.getUuid());
        }

        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }

        processSave(r, index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        processDelete(uuid, index);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void processDelete(String uuid, int index);

    protected abstract void processSave(Resume resume, int index);
}