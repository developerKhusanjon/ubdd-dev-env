package uz.ciasev.ubdd_service.config.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SuccessDataIterable<T> {

    private List<T> objects;
    private Pagination pagination;

    public SuccessDataIterable(Iterable<T> objects) {
        this.objects = new ArrayList<>();
        objects.forEach(this.objects::add);

        if (objects instanceof Page) {

            Page<T> page = (Page<T>) objects;
            pagination = new Pagination(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    page.getTotalElements());

        } else if (objects instanceof Collection) {

            Collection<T> collection = (Collection<T>) objects;
            pagination = new Pagination(
                    0,
                    collection.size(),
                    1,
                    collection.size());

        }
    }
}
