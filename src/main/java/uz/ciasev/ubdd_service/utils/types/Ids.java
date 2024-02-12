package uz.ciasev.ubdd_service.utils.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class Ids {

    private List<Long> ids = new ArrayList<>();

    public Ids(List<Long> ids) {
        this.ids = (ids != null) ? ids : Collections.emptyList();
    }
}
