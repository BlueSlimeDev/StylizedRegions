package me.blueslime.stylizedregions.storage;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtil {

    public static <K, V> Type getType(K k, V v) {
        TypeToken<Map<K, V>> token = new TypeToken<Map<K, V>>() {};
        return token.getType();
    }

}
