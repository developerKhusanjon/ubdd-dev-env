package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CourtAliasedAbstractDictEntity<T extends Enum<T>> extends CourtAbstractDictEntity implements AliasedDictEntity<T> {

    @Getter
    @Enumerated(EnumType.STRING)
    protected T alias;
}
