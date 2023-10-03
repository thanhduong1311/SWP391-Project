package com.app.task.dto.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageResponse<TEntity, TResponse> {
    private int pageNum;
    private int pageSize;
    private long total;
    private String sortBy;
    private Sort.Direction sortDir;
    private Collection<TResponse> data;

    public PageResponse(Page<TEntity> page, Function<TEntity, TResponse> mapper) {
        this.pageNum = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.total = page.getTotalElements();
        this.data = page.getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());

        if (!page.getSort().isUnsorted() && !page.getSort().isEmpty()) {
            page.getSort().stream().findFirst().ifPresent(sortItem -> {
                sortBy = sortItem.getProperty();
                sortDir = sortItem.getDirection();
            });

        }
    }

}
