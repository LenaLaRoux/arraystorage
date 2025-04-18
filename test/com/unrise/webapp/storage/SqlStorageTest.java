package com.unrise.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
