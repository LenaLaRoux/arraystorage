package com.unrise.webapp.storage;

import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    protected Storage storage;
    protected static final String UUID1 = "uuid1";
    protected static final String UUID2 = "uuid2";
    protected static final String UUID3 = "uuid3";

    @BeforeEach
    void setUp() {
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID2);
        assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        assertSame(resume, storage.get(UUID2));
        assertEquals(resume, storage.get(UUID2));
    }

    @Test
    void updateNotExist() {
        Resume resume = new Resume("dummy");
        assertThrowsExactly(NotExistStorageException.class, () -> storage.update(resume));
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
        assertEquals(3, storage.getAll().length);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
        assertEquals(0, storage.getAll().length);
    }

    @Test
    void getAll() {
        assertEquals(3, storage.size());
        assertEquals(3, storage.getAll().length);
        assertEquals(storage.getAll()[0], storage.get(UUID1));
        assertEquals(storage.getAll()[1], storage.get(UUID2));
        assertEquals(storage.getAll()[2], storage.get(UUID3));
    }

    @Test
    void get() {
        assertEquals(storage.getAll()[0], storage.get(UUID1));
        assertEquals(storage.getAll()[1], storage.get(UUID2));
        assertEquals(storage.getAll()[2], storage.get(UUID3));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void saveNoSpaceLeft() {
        Field storageLimit = assertDoesNotThrow(() -> AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT"));
        storageLimit.setAccessible(true);
        int STORAGE_LIMIT = (Integer) assertDoesNotThrow(() -> storageLimit.get(null));

        int i = storage.size();
        while (i < STORAGE_LIMIT) {
            int iFinal = ++i;
            assertDoesNotThrow(() -> storage.save(new Resume("uuid" + iFinal)));
        }

        String nextUuid = "uuid" + (STORAGE_LIMIT + 1);
        StorageException storageException = assertThrowsExactly(
                StorageException.class, () -> storage.save(new Resume(nextUuid)));
        assertEquals(nextUuid, storageException.getUuid());
    }
}