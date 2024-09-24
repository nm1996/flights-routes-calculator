package com.route.flights.mapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Mapper<T, E> {

    T mapToDto(E e);
    E mapToEntity(T t);

    default void mapString(Supplier<String> supplier, Consumer<String> consumer){
        consumer.accept(supplier.get());
    }
    default void mapLong(Supplier<Long> supplier, Consumer<Long> consumer){
        consumer.accept(supplier.get());
    }
    default void mapDouble(Supplier<Double> supplier, Consumer<Double> consumer){
        consumer.accept(supplier.get());
    }
}
