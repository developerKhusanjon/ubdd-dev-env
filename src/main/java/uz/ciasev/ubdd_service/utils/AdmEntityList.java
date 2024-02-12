package uz.ciasev.ubdd_service.utils;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AdmEntityList<T extends AdmEntity> {

    @Getter
    private final Collection<T> list;

    @Getter
    private final Map<Long, T> map;

    public AdmEntityList(Collection<T> list) {
        this.list = list;
        this.map = list.stream()
                .collect(Collectors.toMap(
                        AdmEntity::getId,
                        v -> v)
                );
    }

    public T getById(Long id) {
        return map.get(id);
    }

    public Optional<T> findId(Long id) {
        return Optional.ofNullable(getById(id));
    }

    public Set<Long> getIds() {
        return list.stream().map(AdmEntity::getId).collect(Collectors.toSet());
    }

}
