package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final String NO_SPACE_LEFT = "ERROR: Resume database is full";

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected Integer ensureResume(String uuid) {
        int foundIndex = getIndex(uuid);
        if (foundIndex < 0) {
            throw new NotExistStorageException(uuid);
        }
        return foundIndex;
    }

    @Override
    protected Integer ensureNoResume(String uuid) {
        int foundIndex = getIndex(uuid);

        if (foundIndex >= 0) {
            throw new ExistStorageException(uuid);
        }
        return foundIndex;
    }

    @Override
    protected void processUpdate(Integer index, Resume r) {
        storage[index] = r;
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

    @Override
    protected Resume processGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void processSave(Integer index, Resume r) {
        if (size >= storage.length)
            throw new StorageException(NO_SPACE_LEFT, r.getUuid());
        processSaveArray(r, index);
        size++;
    }

    @Override
    protected void processDelete(Integer index, String uuid) {
        processDeleteArray(uuid, index);
        storage[--size] = null;
    }

    protected abstract void processDeleteArray(String uuid, int index);

    protected abstract void processSaveArray(Resume resume, int index);
}