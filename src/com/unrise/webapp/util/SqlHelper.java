package com.unrise.webapp.util;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.Resume;
import com.unrise.webapp.sql.IConnectionFactory;
import com.unrise.webapp.storage.ISqlRunAndReturn;
import org.junit.platform.commons.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper {

    private static final String PRIMARY_KEY_DUPLICATED_CODE = "23505";
    private static SqlHelper INST;
    private final IConnectionFactory connectionFactory;

    private SqlHelper(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static SqlHelper getInst(IConnectionFactory connectionFactory) {
        if (INST == null) {
            INST = new SqlHelper(connectionFactory);
        }
        return INST;
    }

    public void executeUpdate(String sql, String uuid, String... props) {
        prepareAndReturn(sql, ps -> {
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, uuid, props);
    }

    public void executeStatement(String sql, String... props) {
        prepareAndReturn(sql, ps -> {
            ps.execute();
            return null;
        },null,  props);
    }

    public void executeStatement(String statement) {
        executeStatement(statement, (String[])null);
    }

    public List<Resume> select(String select, String uuid) {
        final boolean isNotSetUuid = StringUtils.isBlank(uuid);
        return prepareAndReturn(select,
                ps -> {
                    if (!isNotSetUuid) {
                        ps.setString(1, uuid);
                    }

                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (rs.next()) {
                        String uuidFact = isNotSetUuid ? rs.getString("uuid").trim() : uuid;
                        resumes.add(new Resume(uuidFact, rs.getString("full_name")));
                    }

                    return resumes;
                },
                uuid
        );

    }

    public int count(String select, String uuid) {
        final boolean isNotSetUuid = StringUtils.isBlank(uuid);
        return prepareAndReturn(select,
                ps -> {
                    if (!isNotSetUuid) {
                        ps.setString(1, uuid);
                    }

                    ResultSet rs = ps.executeQuery();
                    return rs.next()? rs.getInt(1) : 0;
                },
                uuid
        );

    }

    public List<Resume> select(String select) {
        return select(select, null);
    }

    private <T> T prepareAndReturn(String sql, ISqlRunAndReturn<T> sqlExecute, String uuid, String... props) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (props != null ) {
                for (int i = 0; i < props.length; i++) {
                    ps.setString(i + 1, props[i]);
                }
            }
            return sqlExecute.accept(ps);
        } catch (SQLException ex) {
            switch (ex.getSQLState()){
                case PRIMARY_KEY_DUPLICATED_CODE: throw new ExistStorageException(uuid);
                default: throw new StorageException(ex);
            }

        }
    }
}
