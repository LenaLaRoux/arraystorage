package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (r == null) {
            return;
        }

        int foundIndex = getIndex(r.getUuid());

        if (size >= storage.length) {
            throw new StorageException(NO_SPACE_LEFT, r.getUuid());
        } else if (foundIndex >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage[size++] = r;
        }
    }

    @Override
    protected void processSave(Resume resume, int index) {
        storage[size++] = resume;
    }

    @Override
    protected void processDelete(String uuid, int index) {
        size--;
        storage[index] = storage[size];
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
