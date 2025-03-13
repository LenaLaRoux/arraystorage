package com.unrise.webapp.storage;

import com.unrise.webapp.exception.StorageException;

import java.io.IOException;

@FunctionalInterface
public interface IDataStreamConsumer<T> {
    void accept(T param) throws IOException;

    default void acceptWrapper(T param) {
        try {
            this.accept(param);
        } catch (IOException e) {
            throw new StorageException(null, e);
        }
    }
}
