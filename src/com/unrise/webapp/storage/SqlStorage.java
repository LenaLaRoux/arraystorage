package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.Resume;
import com.unrise.webapp.sql.IConnectionFactory;
import com.unrise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.util.List;

public class SqlStorage implements Storage {
    public final IConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.getInst(connectionFactory)
                .executeStatement("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> resumes = getByUuid(uuid);
        if (resumes.isEmpty()) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);
    }

    private List<Resume> getByUuid(String uuid) {
        return SqlHelper.getInst(connectionFactory)
                .select("SELECT * FROM resume r WHERE r.uuid =?", uuid);
    }

    @Override
    public void update(Resume r) {
        SqlHelper.getInst(connectionFactory)
                .executeUpdate("UPDATE resume SET full_name = ? WHERE uuid = ?", r.getUuid(), r.getFullName(), r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (!getByUuid(r.getUuid()).isEmpty()) {
            throw new ExistStorageException(r.getUuid());
        }
        SqlHelper.getInst(connectionFactory)
                .executeStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), r.getFullName());
    }

    @Override
    public void delete(String uuid) {
        get(uuid);
        SqlHelper.getInst(connectionFactory)
                .executeStatement("DELETE FROM resume WHERE uuid = ?", uuid);
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = SqlHelper.getInst(connectionFactory)
                .select("SELECT * FROM resume");
        return resumes.isEmpty() ? new Resume[0] : resumes.toArray(new Resume[0]);
    }

    @Override
    public Resume[] getAllSorted() {
        List<Resume> resumes = SqlHelper.getInst(connectionFactory)
                .select("SELECT * FROM resume ORDER BY full_name, uuid");
        return resumes.isEmpty() ? new Resume[0] : resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return SqlHelper.getInst(connectionFactory)
                .select("SELECT * FROM resume").size();
    }
}
