package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final IStreamSerializer strategy;

    protected PathStorage(String dir, IStreamSerializer strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy maust not be null");
        this.strategy = strategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            for (Path path : getListOfPathFiles(directory)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new StorageException("Path delete error", e);
        }
    }

    @Override
    public int size() {
        List<Path> list = getListOfPathFiles(directory);
        if (list == null) {
            throw new StorageException("Directory read error");
        }
        return list.size();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        List<Path> list = getListOfPathFiles(directory);
        return list.stream()
                .filter(path -> uuid.equals(path.getFileName().toString()))
                .findAny()
                .orElse(null);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            if (!Files.isWritable(path)) {
                throw new StorageException("Cannot write to file", path.toAbsolutePath().toString());
            }

            strategy.doWrite(resume, new ObjectOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isFound(Path path) {
        Path parentDir = path.getParent();
        return Files.isRegularFile(path)
                && parentDir != null
                && parentDir.equals(directory);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Path createdFile = Files.createFile(path);
            strategy.doWrite(resume, new ObjectOutputStream(Files.newOutputStream(createdFile)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create path ", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new ObjectInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path, String uuid) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Unable to delete path", path.toString());
        }

    }

    @Override
    public Resume[] getAll() {
        List<Path> paths = getListOfPathFiles(directory);
        return paths.stream()
                .map(this::doGet)
                .toArray(Resume[]::new);
    }


    @Override
    protected List<Resume> doCopyAll() {
        List<Path> paths = getListOfPathFiles(directory);
        return paths.stream()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    public List<Path> getListOfPathFiles(Path directory) {
        if (!Files.isDirectory(directory)) {
            throw new StorageException("Not a directory", directory.toString());
        }

        try (Stream<Path> list = Files.list(directory)) {
            return list.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}