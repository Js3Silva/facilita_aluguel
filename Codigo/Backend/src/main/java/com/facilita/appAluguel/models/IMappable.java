package com.facilita.appAluguel.models;

import com.facilita.appAluguel.utils.ObjectMapperConfig;

public interface IMappable<T> {

    default T toEntity(Class<T> entityClass) {
        return ObjectMapperConfig.OBJECT_MAPPER.convertValue(this, entityClass);
    }
}
