package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }

    @Override
    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
