package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeEach
    void setUp() {
        storage = new ArrayStorage();
        super.setUp();
    }

    @Test
    void save() {
        assertEquals(3, storage.size());

        String uuid4 = "uuid4";
        String uuid5 = "uuid5";

        Resume r5 = new Resume(uuid5);
        storage.save(r5);
        assertEquals(4, storage.size());
        assertSame(r5, storage.getAll()[3]);

        Resume r4 = new Resume(uuid4);
        storage.save(r4);
        assertEquals(5, storage.size());
        assertSame(r4, storage.getAll()[4]);
        assertSame(r5, storage.getAll()[3]);

        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID1)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID2)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID3)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(uuid4)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(uuid5)));
        assertEquals(5, storage.size());
    }

    @Test
    void delete() {
        assertEquals(3, storage.size());
        assertEquals(storage.getAll()[0], storage.get(UUID1));
        assertEquals(storage.getAll()[1], storage.get(UUID2));
        assertEquals(storage.getAll()[2], storage.get(UUID3));

        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete("dummy"));

        storage.delete(UUID1);
        assertEquals(2, storage.size());
        assertEquals(storage.getAll()[0], storage.get(UUID3));
        assertEquals(storage.getAll()[1], storage.get(UUID2));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID1));

        storage.delete(UUID2);
        assertEquals(1, storage.size());
        assertEquals(storage.getAll()[0], storage.get(UUID3));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID2));

        storage.delete(UUID3);
        assertEquals(0, storage.size());
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID3));

        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID1));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID2));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID3));
    }
}