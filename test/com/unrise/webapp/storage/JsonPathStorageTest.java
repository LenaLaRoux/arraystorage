package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;
import com.unrise.webapp.storage.serializer.JsonStreamSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
