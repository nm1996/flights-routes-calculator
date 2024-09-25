package com.route.flights.warehouse;

import com.route.flights.dto.DTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Warehouse<T extends DTO, E extends Long> {

    Map<Long,T> getCached();

    default void putInCache(T t){
        getCached().put(t.getId(), t);
    }

    default void putInCache(E e, T t){
        getCached().put(e,t);
    }

    default void addListToCache(List<T> tList){
        Map<Long, T> mapFromList = tList.stream()
                .collect(Collectors.toMap(
                        DTO::getId,
                        val -> val
                ));
        getCached().putAll(mapFromList);
    }

    default Optional<T> getById(E e){
        return Optional.of(getCached().get(e));
    }

    default boolean isMissed(T t){
        return !getCached().containsValue(t);
    }

    default boolean isPresent(T t){
        return getCached().containsValue(t);
    }

    default boolean isPresentById(E e){
        return getCached().containsKey(e);
    }

    default void clear(){
        getCached().clear();
    }

    default Optional<T> findCacheMatch(T t){
        if(getCached().containsValue(t)){
            return getCached().entrySet().stream()
                    .filter( kv -> kv.getValue().equals(t))
                    .findFirst()
                    .map(Map.Entry::getValue);
        }
        return Optional.empty();
    }
}
