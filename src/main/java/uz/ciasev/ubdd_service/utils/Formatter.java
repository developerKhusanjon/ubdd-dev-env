package uz.ciasev.ubdd_service.utils;

@FunctionalInterface
public interface Formatter<T> {
    String formatToString(T obj);
}
