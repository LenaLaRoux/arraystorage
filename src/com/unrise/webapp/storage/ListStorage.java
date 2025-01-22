package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (uuid.equals(r.getUuid()))
                return i;
        }
        return -1;
    }

    @Override
    protected Integer ensureResume(String uuid) {
        int position = getIndex(uuid);
        if (position < 0) {
            throw new NotExistStorageException(uuid);
        }
        return position;
    }

    @Override
    protected void processUpdate(Integer index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected Integer ensureNoResume(String uuid) {
        int position = getIndex(uuid);
        if (position >= 0) {
            throw new ExistStorageException(uuid);
        }
        return null;
    }

    @Override
    protected void processSave(Integer index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void processDelete(Integer index, String uuid) {
        int position = getIndex(uuid);
        storage.remove(position);
    }

    @Override
    protected Resume processGet(Integer index) {
        return storage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

}
