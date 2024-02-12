package uz.ciasev.ubdd_service.repository.dict;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;
import uz.ciasev.ubdd_service.specifications.dict.DistrictSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.util.List;


@Component
@AllArgsConstructor
public class DictionaryFilterBean {

    public static <T extends AbstractDict> FilterHelper<T> getBase() {
        List<Pair<String, Filter<T>>> baseFilters = getBaseFilters();
        return new FilterHelper<T>(baseFilters);
    }

    public static FilterHelper<District> getForDistrict() {
        List<Pair<String, Filter<District>>> baseFilters = getBaseFilters();
        return new FilterHelper<District>(
                baseFilters,
                Pair.of("regionId", new LongFilter<>(DistrictSpecifications::withRegionId))
        );
    }

    public static <T extends AbstractDict> List<Pair<String, Filter<T>>> getBaseFilters() {
        return List.of(
                Pair.of("isActive", new BooleanFilter<>(DictionarySpecifications::withIsActive)),
                Pair.of("name", new StringFilter<>(DictionarySpecifications::withNameLike)),
                Pair.of("code", new StringFilter<>(DictionarySpecifications::withCode))
        );
    }
}
