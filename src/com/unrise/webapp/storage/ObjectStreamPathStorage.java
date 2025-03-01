package com.unrise.webapp.storage;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory, new StrategyContextBuilder()
                .addWrStrategy(new ObjectStreamWRStrategy()));
    }
}
