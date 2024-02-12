package uz.ciasev.ubdd_service.config.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Data
@AllArgsConstructor
public class Pagination {
    private int page;
    private int limit;
    private int totalPages;
    private long totalObjects;

    public Pagination(Collection collection) {
        this(0,
                collection.size(),
                1,
                collection.size());
    }

    public Pagination(Page page) {
        this(page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements());
    }
}
