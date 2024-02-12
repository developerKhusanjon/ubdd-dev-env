package uz.ciasev.ubdd_service.specifications.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {

    @JsonProperty("result")
    List<T> content = new ArrayList<>();
    Boolean isFirst;
    Boolean isLast;

    @JsonProperty("current")
    Integer pageNumber;

    Integer totalPages;

    @JsonProperty("count")
    Long totalElements;

    String previous;
    String next;

}
