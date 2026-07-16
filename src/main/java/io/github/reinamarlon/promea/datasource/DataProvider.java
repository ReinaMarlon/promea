package io.github.reinamarlon.promea.datasource;

@FunctionalInterface
public interface DataProvider<T> {

    T get();
}
