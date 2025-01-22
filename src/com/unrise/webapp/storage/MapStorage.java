package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected String ensureResume(String uuid) {
        String index = getIndex(uuid);
        Resume found = storage.get(index);
        if (found == null) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    @Override
    protected String ensureNoResume(String uuid) {
        String index = getIndex(uuid);
        Resume found = storage.get(index);
        if (found != null) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    protected void processUpdate(String index, Resume r) {
        storage.replace(index, r);
    }

    @Override
    protected void processSave(String index, Resume r) {
        storage.put(index, r);
    }

    @Override
    protected Resume processGet(String index) {
        return storage.get(index);
    }

    @Override
    protected void processDelete(String index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
