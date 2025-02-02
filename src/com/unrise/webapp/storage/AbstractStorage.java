package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public void update(Resume r) {
        T index = getExistingSearchKey(r.getUuid());
        doUpdate(index, r);
    }

    @Override
    public void save(Resume r) {
        T insertIndex = getNotExistingSearchKey(r.getUuid());
        doSave(insertIndex, r);
    }

    @Override
    public void delete(String uuid) {
        T insertIndex = getExistingSearchKey(uuid);
        doDelete(insertIndex, uuid);
    }

    @Override
    public Resume get(String uuid) {
        T insertIndex = getExistingSearchKey(uuid);
        return doGet(insertIndex);
    }

    abstract protected boolean isFound(T key);

    protected abstract T getSearchKey(String uuid);

    protected T getNotExistingSearchKey(String uuid) {
        T foundIndex = getSearchKey(uuid);
        if (isFound(foundIndex)) {
            throw new ExistStorageException(uuid);
        }
        return foundIndex;
    }

    protected T getExistingSearchKey(String uuid) {
        T foundIndex = getSearchKey(uuid);
        if (!isFound(foundIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return foundIndex;
    }

    @Override
    public Resume[] getAllSorted() {

        Comparator<Resume> compare = Comparator.comparing(Resume::getFullName, Comparator.nullsLast((a, b) ->
        {
            if (Objects.equals(a, b))
                return 0;
            else if (a == null)
                return 1;
            else if (b == null)
                return -1;
            else return a.compareTo(b);
        })).thenComparing(Resume::getUuid);

        Resume[] resumes = getAll();
        Arrays.sort(resumes, compare);
        return resumes;
    }

    protected abstract void doUpdate(T index, Resume r);

    protected abstract void doSave(T index, Resume r);

    protected abstract void doDelete(T index, String uuid);

    protected abstract Resume doGet(T index);
}
