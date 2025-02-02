package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveNoSpaceLeft() {
        int i = storage.size();
        while (i < AbstractArrayStorage.STORAGE_LIMIT) {
            int iFinal = ++i;
            assertDoesNotThrow(() -> storage.save(new Resume("uuid" + iFinal)));
        }

        String nextUuid = "uuid" + (AbstractArrayStorage.STORAGE_LIMIT + 1);
        StorageException storageException = assertThrowsExactly(
                StorageException.class, () -> storage.save(new Resume(nextUuid)));
        assertEquals(nextUuid, storageException.getUuid());
    }
}