package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (r == null) {
            return;
        }

        int index = getIndex(r.getUuid());
        if (size > storage.length) {
            System.out.println(NO_SPACE_LEFT);
        }else if (index >= 0) {
            System.out.println(IS_IN_DB);
        }else {
            index = Math.abs(index);

            Resume[] partAfterIndex = Arrays.copyOfRange(storage, index - 1, size);
            storage[index - 1] = r;
            System.arraycopy(partAfterIndex, 0, storage, index, partAfterIndex.length);
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println(NOT_IN_DB);
            return;
        }

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
