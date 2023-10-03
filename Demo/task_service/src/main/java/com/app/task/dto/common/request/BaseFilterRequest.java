package com.app.task.dto.common.request;

import com.app.task.util.TrimString;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseFilterRequest<T> {
    @JsonDeserialize(using = TrimString.class)
    private String sortBy;

    private Sort.Direction sortDir = Sort.Direction.ASC;

    public Sort getSort() {

        return sortBy != null
                ? Sort.by(sortDir, sortBy)
                : Sort.unsorted();
    }

    public abstract Specification<T> getSpecification();
}
