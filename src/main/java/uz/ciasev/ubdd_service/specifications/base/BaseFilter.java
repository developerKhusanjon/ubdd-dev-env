package uz.ciasev.ubdd_service.specifications.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public abstract class BaseFilter {

    @Schema(example = "1", description = "This is page number. If you don't send this parameter, then page number will be 1.")
    private Integer page = 1;

    @Schema(example = "10", description = "This is rows per page. If you don't send this parameter, then rows per page will be 10.")
    @JsonProperty("page_size")
    private Integer rowsPerPage = 10;

    @Schema(example = "null", description = "This is sort field. If you don't send this parameter, then sort field will be 'id'.")
    private String sort = "id";

    @Schema(example = "null", description = "This is sort direction. If you don't send this parameter, then sort direction will be 'DESC'.")
    private Boolean descending = true;

    public Sort sorting() {
        return Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, this.sort);
    }

    public Pageable pageable() {
        return PageRequest.of(this.page - 1, this.rowsPerPage, this.sorting());
    }

}
