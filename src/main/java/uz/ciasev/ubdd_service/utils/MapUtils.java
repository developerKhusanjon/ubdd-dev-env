package uz.ciasev.ubdd_service.utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapUtils {

    public static <K, V> Map<K, V> filter(Map<K, V> map, Set<K> keys) {
        return map.entrySet().stream()
                .filter(e -> keys.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <K> boolean keysNotEmpty(Map<K, String> map, Set<K> keys) {
        return keys.stream()
                .map(map::get)
                .allMatch(value -> value != null && !value.isBlank());
    }
}
