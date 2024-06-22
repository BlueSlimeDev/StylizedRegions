package me.blueslime.stylizedregions.modules.storage;

import com.google.gson.Gson;
import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.logs.MeteorLogger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JsonData<K, V> {

    private static final Gson GSON = new Gson();

    private Map<K, V> cache;

    private final Type type;

    private final File file;

    public <TK, TV> JsonData(File folder, String name, TK tk, TV tv) {
        this.file = new File(
                folder,
                name + ".json"
        );

        this.type = JsonUtil.getType(
            tk,
            tv
        );

        this.cache = load();
    }

    private Map<K, V> load() {
        if (file.exists()) {
            try (FileInputStream input = new FileInputStream(file)) {
                return GSON.fromJson(
                    new InputStreamReader(input),
                    type
                );
            } catch (IOException exception) {
                Implements.fetch(MeteorLogger.class).error(exception, "Failed to load" + file.getName() + ", Error:");
            }
        }
        return new ConcurrentHashMap<>();
    }


    public void update(K key, V value) {

        cache.put(key, value);

        save();
    }

    public V computeIfAbsent(K key, V value) {
        if (!cache.containsKey(key)) {
            cache.put(key, value);
        }

        save();

        return cache.get(key);
    }

    public void save() {
        try (
                FileOutputStream output = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(output)
        ) {
            GSON.toJson(cache, type, writer);
            writer.flush();
            output.flush();
        } catch (IOException exception) {
            Implements.fetch(MeteorLogger.class).error(exception, "Failed to " + file.getName() + ", Error:");
        }
    }

    public V getValue(K key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        return null;
    }

    public void replaceMap(Map<K, V> map) {
        this.cache = map;
    }

    public Map<K, V> toMap() {
        return cache;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "type=" + type +
                ", cache=" + cache +
                ", file=" + file +
                '}';
    }
}

