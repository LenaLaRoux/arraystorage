package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

abstract public class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\topjava\\storage");
    protected final Storage storage;
    protected static final String UUID1 = "uuid1";
    protected static final String UUID2 = "uuid2";
    protected static final String UUID2_FIO1 = "uuid2_fio1";
    protected static final String UUID2_FIO2 = "uuid2_fio2";
    protected static final String UUID3_FIO2 = "uuid3_fio2";
    protected static final String UUID3 = "uuid3";
    protected static final String UUID4 = "uuid4";
    protected static final String UUID5 = "uuid5";
    protected static final Resume r1 = new Resume(UUID1);
    protected static final Resume r2 = new Resume(UUID2);
    protected static final Resume r2Fio1 = new Resume(UUID2_FIO1, "Fio1");
    protected static final Resume r2Fio2 = new Resume(UUID2_FIO2, "Fio2");
    protected static final Resume r3Fio1 = new Resume(UUID3_FIO2, "Fio1");
    protected static final Resume r3 = new Resume(UUID3);
    protected static final Resume r4 = new Resume(UUID4);
    protected static final Resume r5 = new Resume(UUID5);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
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

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
        assertSame(resume, storage.get(resume.getUuid()));
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
        assertGet(r1);
        assertGet(r2);
        assertGet(r3);
    }

    @Test
    void get() {
        for (Resume r : storage.getAll()) {
            assertGet(r);
        }
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get("dummy"));
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

    @Test
    void getAllSorted() {
        assertSize(3);
        storage.save(r3Fio1);
        storage.save(r5);
        storage.save(r2Fio1);
        storage.save(r2Fio2);
        storage.save(r4);
        Resume[] expected = {r2Fio1, r3Fio1, r2Fio2, r1, r2, r3, r4, r5};
        Resume[] sorted = storage.getAllSorted();
        assertArrayEquals(expected, sorted);
    }

}
