package com.app.task.dto.common.request;

import com.app.task.config.BaseConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePageFilterRequest<T> {
    @PositiveOrZero
    private int pageNum = BaseConstants.DEFAULT_PAGE_NUMBER;
    @Positive
    @Max(200)
    private int pageSize = BaseConstants.DEFAULT_PAGE_SIZE;

    private String sortBy;
    private Sort.Direction sortDir = Sort.Direction.ASC;

    public Pageable getPageable() {
        int pageNumber = this.pageNum > 0
                ? this.pageNum
                : BaseConstants.DEFAULT_PAGE_NUMBER;
        int pageSize = this.pageSize > 0 && this.pageSize <= BaseConstants.DEFAULT_MAX_PAGE_SIZE
                ? this.pageSize
                : BaseConstants.DEFAULT_PAGE_SIZE;

        return sortBy != null
                ? PageRequest.of(pageNumber - 1, pageSize, sortDir, sortBy)
                : PageRequest.of(pageNumber - 1, pageSize);
    }

    public abstract Specification<T> getSpecification();
}
