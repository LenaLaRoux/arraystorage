package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public void update(Resume r) {
        T index = ensureResume(r.getUuid());
        processUpdate(index, r);
    }

    @Override
    public void save(Resume r) {
        T insertIndex = ensureNoResume(r.getUuid());
        processSave(insertIndex, r);
    }

    @Override
    public void delete(String uuid) {
        T insertIndex = ensureResume(uuid);
        processDelete(insertIndex, uuid);
    }

    @Override
    public Resume get(String uuid) {
        T insertIndex = ensureResume(uuid);
        return processGet(insertIndex);
    }

    protected abstract T getIndex(String uuid);

    protected abstract T ensureResume(String uuid);

    protected abstract T ensureNoResume(String uuid);

    protected abstract void processUpdate(T index, Resume r);

    protected abstract void processSave(T index, Resume r);

    protected abstract void processDelete(T index, String uuid);

    protected abstract Resume processGet(T index);
}
