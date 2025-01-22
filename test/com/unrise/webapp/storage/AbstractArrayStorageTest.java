package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
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