package com.route.flights.warehouse;

import com.route.flights.dto.DTO;

import java.util.List;
import java.util.Optional;

public interface Warehouse<T extends DTO, E> {

    List<T> getCached();

    default void addToList(T t){
        getCached().add(t);
    }

    default void addListToList(List<T> tList){
        getCached().addAll(tList);
    }

    default Optional<T> getById(E e){
        return getCached().stream()
                .filter(elem -> elem.getId() == e)
                .findFirst();
    }

    default boolean isMissed(T t){
        return getCached().stream().noneMatch(elem -> elem.equals(t));
    }

    default boolean isPresent(T t){
        return getCached().stream().anyMatch(elem -> elem.equals(t));
    }

    default void clear(){
        getCached().clear();
    }

    default Optional<T> findCacheMatch(T t){
        return getCached().stream()
                .filter(elem -> elem.equals(t))
                .findFirst();
    }
}
