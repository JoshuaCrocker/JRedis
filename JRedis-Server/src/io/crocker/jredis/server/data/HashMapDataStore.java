package io.crocker.jredis.server.data;

import io.crocker.jredis.server.data.exception.MissingKeyException;

import java.util.*;

public class HashMapDataStore extends DataStore {

    private Map<String, Object> data = new HashMap<>();

    @Override
    public Object get(String key) throws MissingKeyException {
        if (this.data.containsKey(key)) {
            return this.data.get(key);
        }

        throw new MissingKeyException();
    }

    @Override
    public boolean set(String key, Object value) {
        this.data.put(key, value);
        return true;
    }

    @Override
    public List<String> all() {
        Set<String> keys = this.data.keySet();
        Iterator iKeys = keys.iterator();

        ArrayList<String> all = new ArrayList<>();

        while (iKeys.hasNext()) {
            String key = iKeys.next().toString();
            all.add(key);

            try {
                all.add(this.get(key).toString());
            } catch (MissingKeyException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        return all;
    }

    @Override
    public int flush() {
        int size = this.data.size();
        this.data.clear();
        return size;
    }

    public boolean delete(String key) {
        this.data.remove(key);
        return true;
    }

    @Override
    public boolean has(String key) {
        return this.data.containsKey(key);
    }
}
