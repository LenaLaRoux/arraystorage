package com.unrise.webapp.storage;

import com.unrise.webapp.model.Resume;
import com.unrise.webapp.storage.serializer.XmlStreamSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}