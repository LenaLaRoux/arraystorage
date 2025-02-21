package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final String NO_SPACE_LEFT = "ERROR: Resume database is full";

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void doUpdate(Integer index, Resume r) {
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
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doSave(Integer index, Resume r) {
        if (size >= storage.length)
            throw new StorageException(NO_SPACE_LEFT, r.getUuid());
        processSaveArray(r, index);
        size++;
    }

    @Override
    protected void doDelete(Integer index, String uuid) {
        processDeleteArray(uuid, index);
        storage[--size] = null;
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected boolean isFound(Integer key) {
        return key >= 0;
    }

    protected abstract void processDeleteArray(String uuid, int index);

    protected abstract void processSaveArray(Resume resume, int index);
}