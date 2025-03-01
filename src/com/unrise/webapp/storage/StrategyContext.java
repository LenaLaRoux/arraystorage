package com.unrise.webapp.storage;

public class StrategyContext {
    private IWriteReadStrategy writeReadStrategy;

    public void setWrStrategy(IWriteReadStrategy strategy) {
        this.writeReadStrategy = strategy;
    }

    public IWriteReadStrategy wrStrategy() {
        return writeReadStrategy;
    }

}
