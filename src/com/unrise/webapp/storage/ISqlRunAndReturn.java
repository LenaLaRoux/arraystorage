package com.unrise.webapp.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ISqlRunAndReturn<T> {
    T accept(PreparedStatement ps) throws SQLException;
}
