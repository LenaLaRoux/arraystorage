package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final IStreamSerializer strategy;

    protected FileStorage(File directory, IStreamSerializer strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy maust not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.strategy = strategy;
        this.directory = directory;
    }

    @Override
    public void clear() {
        List<File> fileList = getCheckedListFiles(directory);
        for (File file : fileList) {
            doDelete(file, null);
        }
    }

    @Override
    public int size() {
        List<File> fileList = getCheckedListFiles(directory);
        return fileList.size();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
            if (file.createNewFile())
                strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
            else {
                throw new StorageException("Unable to create file", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file, String uuid) {
        if (!file.delete()) {
            throw new StorageException("Unable to delete file", file.getName());
        }
    }

    @Override
    public Resume[] getAll() {
        List<File> fileList = getCheckedListFiles(directory);
        return fileList.stream()
                .map(this::doGet)
                .toArray(Resume[]::new);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<File> fileList = getCheckedListFiles(directory);
        return fileList.stream()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    public List<File> getCheckedListFiles(File directory) {
        if (!directory.isDirectory()) {
            throw new StorageException("Not a directory", directory.getAbsolutePath());
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("No files found", directory.getAbsolutePath());
        }

        return Arrays.asList(files);
    }
}
