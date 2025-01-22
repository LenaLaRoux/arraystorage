package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortedArrayStorageTest extends AbstractArrayStorageTest {


    @BeforeEach
    void setUp() {
        storage = new SortedArrayStorage();
        super.setUp();
    }

    @Test
    void sortedOrder() {
        storage.save(r5);
        Resume[] expected1 = {r1, r2, r3, r5};
        assertArrayEquals(expected1, storage.getAll());

        storage.save(r4);
        Resume[] expected2 = {r1, r2, r3, r4, r5};
        assertArrayEquals(expected2, storage.getAll());

        storage.delete(UUID3);
        Resume[] expected3 = {r1, r2, r4, r5};
        assertArrayEquals(expected3, storage.getAll());

        storage.delete(UUID1);
        Resume[] expected4 = {r2, r4, r5};
        assertArrayEquals(expected4, storage.getAll());

        storage.delete(UUID5);
        Resume[] expected5 = {r2, r4};
        assertArrayEquals(expected5, storage.getAll());
    }
}