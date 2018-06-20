package io.crocker.jredis.server.data;

import io.crocker.jredis.server.data.exception.MissingKeyException;

import java.util.List;

public abstract class DataStore {
    public abstract Object get(String key) throws MissingKeyException;

    public abstract boolean set(String key, Object value);

    public abstract List<String> all();

    public abstract int flush();

    public abstract boolean delete(String key);

    public abstract boolean has(String key);
}