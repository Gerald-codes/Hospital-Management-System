package org.lucas.util;

import java.util.Objects;

/**
 * Utility class to represent a pair of objects
 * @param <T> First class
 * @param <S> Second class
 */
public class Pair<T, S> {
    public final T first;
    public final S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
