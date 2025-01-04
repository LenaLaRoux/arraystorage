package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeEach
    void setUp() {
        storage = new ArrayStorage();
        super.setUp();
    }

    @Test
    void save() {
        Assertions.assertEquals(3, storage.size());

        String uuid4 = "uuid4";
        String uuid5 = "uuid5";

        Resume r5 = new Resume(uuid5);
        storage.save(r5);
        Assertions.assertEquals(4, storage.size());
        Assertions.assertSame(r5, storage.getAll()[3]);

        Resume r4 = new Resume(uuid4);
        storage.save(r4);
        Assertions.assertEquals(5, storage.size());
        Assertions.assertSame(r4, storage.getAll()[4]);
        Assertions.assertSame(r5, storage.getAll()[3]);

        Assertions.assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID1)));
        Assertions.assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID2)));
        Assertions.assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID3)));
        Assertions.assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(uuid4)));
        Assertions.assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(uuid5)));
        Assertions.assertEquals(5, storage.size());
    }

    @Test
    void delete() {
        Assertions.assertEquals(3, storage.size());
        Assertions.assertEquals(storage.getAll()[0], storage.get(UUID1));
        Assertions.assertEquals(storage.getAll()[1], storage.get(UUID2));
        Assertions.assertEquals(storage.getAll()[2], storage.get(UUID3));

        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.delete("dummy"));

        storage.delete(UUID1);
        Assertions.assertEquals(2, storage.size());
        Assertions.assertEquals(storage.getAll()[0], storage.get(UUID3));
        Assertions.assertEquals(storage.getAll()[1], storage.get(UUID2));
        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID1));

        storage.delete(UUID2);
        Assertions.assertEquals(1, storage.size());
        Assertions.assertEquals(storage.getAll()[0], storage.get(UUID3));
        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID2));

        storage.delete(UUID3);
        Assertions.assertEquals(0, storage.size());
        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID3));

        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID1));
        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID2));
        Assertions.assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID3));
    }
}