package me.blueslime.stylizedregions.storage;

import com.google.gson.Gson;
import dev.mruniverse.slimelib.logs.SlimeLogs;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JsonController<K, V> {

    private static final Gson GSON = new Gson();

    private final SlimeLogs logs;

    private Map<K, V> cache;

    private final Type type;

    private final File file;

    public <TK, TV> JsonController(SlimeLogs logs, File folder, String name, TK tk, TV tv) {
        this.logs = logs;

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
                logs.error("Failed to load" + file.getName() + ", Error:", exception);
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
            logs.error("Failed to " + file.getName() + ", Error:", exception);
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
        return "JsonController{" +
                "type=" + type +
                ", cache=" + cache +
                ", file=" + file +
                '}';
    }
}

