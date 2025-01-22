package com.unrise.webapp.storage;

import org.junit.jupiter.api.BeforeEach;

class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeEach
    void setUp() {
        storage = new ArrayStorage();
        super.setUp();
    }
}