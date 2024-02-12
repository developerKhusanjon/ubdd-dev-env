package uz.ciasev.ubdd_service.utils;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.HashMap;
import java.util.Map;

public class TupleUtils {

    public static Map<String, Object> toMap(Tuple tuple) {
        Map<String, Object> map = new HashMap<>();

        for (TupleElement e: tuple.getElements()) {
            map.put(
                    e.getAlias(),
                    tuple.get(e.getAlias())
            );
        }

        return map;
    }
}
