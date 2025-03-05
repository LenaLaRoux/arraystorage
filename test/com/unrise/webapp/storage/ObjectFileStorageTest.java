package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

    @Override
    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

}
