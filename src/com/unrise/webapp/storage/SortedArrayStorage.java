package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void processSave(Resume resume, int index) {
        index = Math.abs(index);

        System.arraycopy(storage, index - 1, storage, index, size - index + 1);
        storage[index - 1] = resume;
        size++;
    }

    @Override
    protected void processDelete(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
