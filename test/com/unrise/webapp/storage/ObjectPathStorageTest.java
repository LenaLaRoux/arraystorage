package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }

    @Override
    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
