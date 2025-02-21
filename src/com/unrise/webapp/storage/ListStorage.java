package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (uuid.equals(r.getUuid()))
                return i;
        }
        return null;
    }

    @Override
    protected void doUpdate(Integer index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected void doSave(Integer index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Integer index, String uuid) {
        int position = getSearchKey(uuid);
        storage.remove(position);
    }

    @Override
    protected Resume doGet(Integer index) {
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

    @Override
    protected boolean isFound(Integer key) {
        return Objects.nonNull(key);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

}
