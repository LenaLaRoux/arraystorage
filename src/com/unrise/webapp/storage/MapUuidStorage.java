package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
    @Override
    protected void doUpdate(String index, Resume r) {
        storage.replace(index, r);
    }

    @Override
    protected void doSave(String index, Resume r) {
        storage.put(index, r);
    }

    @Override
    protected Resume doGet(String index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(String index, String uuid) {
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

    @Override
    protected boolean isFound(String key) {
        return storage.get(key) != null;
    }
}
