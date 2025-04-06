package com.unrise.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest{

    SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(),
                Config.get().getUser(),
                Config.get().getPassword()));
    }
}
