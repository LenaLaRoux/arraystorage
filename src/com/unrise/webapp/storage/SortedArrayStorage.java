package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void processSave(Resume resume, int index) {
        int positiveIndex = Math.abs(index);

        Resume[] partAfterIndex = Arrays.copyOfRange(storage, positiveIndex - 1, size);
        storage[positiveIndex - 1] = resume;
        System.arraycopy(partAfterIndex, 0, storage, positiveIndex, partAfterIndex.length);
        size++;
    }

    @Override
    protected void processDelete(String uuid, int index) {
        Resume[] partAfterIndex = Arrays.copyOfRange(storage, index + 1, size);
        System.arraycopy(partAfterIndex, 0, storage, index, partAfterIndex.length);
        storage[--size] = null;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
