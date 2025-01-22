package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
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
    protected static final String UUID4 = "uuid4";
    protected static final String UUID5 = "uuid5";
    protected static final Resume r1 = new Resume(UUID1);
    protected static final Resume r2 = new Resume(UUID2);
    protected static final Resume r3 = new Resume(UUID3);
    protected static final Resume r4 = new Resume(UUID4);
    protected static final Resume r5 = new Resume(UUID5);

    @BeforeEach
    void setUp() {
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID2);
        assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        assertGet(resume);
    }

    @Test
    void updateNotExist() {
        Resume resume = new Resume("dummy");
        assertThrowsExactly(NotExistStorageException.class, () -> storage.update(resume));
    }

    @Test
    void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
        assertEquals(size, storage.getAll().length);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(storage.getAll(), new Resume[0]);
    }

    @Test
    void getAll() {
        assertSize(3);
        Resume[] expected = {r1, r2, r3};
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void get() {
        assertGet(storage.getAll()[0]);
        assertGet(storage.getAll()[1]);
        assertGet(storage.getAll()[2]);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test
    void save() {
        assertSize(3);

        storage.save(r5);
        assertGet(r5);
        assertSize(4);

        storage.save(r4);
        assertGet(r4);
        assertSize(5);

        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID1)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID2)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID3)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID4)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID5)));
        assertSize(5);
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

    @Test
    void delete() {
        assertSize(3);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete("dummy"));

        storage.delete(UUID1);
        assertSize(2);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID1));

        storage.delete(UUID2);
        assertSize(1);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID2));


        storage.delete(UUID3);
        assertSize(0);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID3));

        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID1));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID2));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID3));
    }
}