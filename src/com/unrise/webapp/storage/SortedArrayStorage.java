package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void processSaveArray(Resume resume, int index) {
        index = Math.abs(index);

        System.arraycopy(storage, index - 1, storage, index, size - index + 1);
        storage[index - 1] = resume;
    }

    @Override
    protected void processDeleteArray(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
