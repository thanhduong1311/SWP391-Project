package com.app.task.dto.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ListResponse<TEntity, TResponse> extends ArrayList<TResponse> {
    public ListResponse(List<TEntity> list, Function<TEntity, TResponse> mapper) {
        List<TResponse> mappedResult = list
                .stream()
                .map(mapper)
                .collect(Collectors.toList());

        addAll(mappedResult);
    }
}
