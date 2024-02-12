package uz.ciasev.ubdd_service.mvd_core.citizenapi;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;

@Getter
public class AliasedDictResponseDTO extends DictResponseDTO{

    private final String alias;

    public <T extends Enum<T>> AliasedDictResponseDTO(AliasedDictEntity<T> dist) {
        super(dist);
        this.alias = dist.getAlias().toString();
    }

    public static <T extends Enum<T>> AliasedDictResponseDTO ofNullable(AliasedDictEntity<T> aliasedDict) {
        if (aliasedDict == null) {
            return null;
        }

        return new AliasedDictResponseDTO(aliasedDict);
    }

}
