package com.unrise.webapp.storage;

public class StrategyContextBuilder {
    private final StrategyContext context;

    public StrategyContextBuilder() {
        context = new StrategyContext();
    }

    public StrategyContext addWrStrategy(IWriteReadStrategy strategy) {
        context.setWrStrategy(strategy);
        return context;
    }
}
