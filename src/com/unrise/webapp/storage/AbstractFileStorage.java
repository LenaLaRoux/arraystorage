package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        List<File> fileList = traverseDirectory(directory);
        for (File file : fileList) {
            if (!file.delete()) {
                String dirOrFile = file.isDirectory() ? "directory " : "file ";
                throw new RuntimeException("Unable to delete " + dirOrFile + file.getAbsolutePath());
            }
        }
    }

    @Override
    public int size() {
        List<File> fileList = traverseDirectory(directory);
        return fileList.size();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isFound(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
           if ( file.createNewFile() )
                doWrite(r, file);
           else {
               throw new StorageException("Unable to create file", file.getName());
           }
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void doDelete(File file, String uuid) {
        if (!file.delete()){
            throw new StorageException("Unable to delete file", file.getName());
        }
    }

    @Override
    public Resume[] getAll() {
        List<File> fileList = traverseDirectory(directory);
        return fileList.stream()
                .map(this::doGet)
                .toArray(Resume[]::new);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<File> fileList = traverseDirectory(directory);
        return fileList.stream()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    public List<File> traverseDirectory(File directory) {
        List<File> fileList = new ArrayList<>();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(file);

                    if (file.isDirectory()) {
                        List<File> innerList = traverseDirectory(file);
                        fileList.addAll(innerList);
                    }
                }
            }
        }
        return fileList;
    }
}
