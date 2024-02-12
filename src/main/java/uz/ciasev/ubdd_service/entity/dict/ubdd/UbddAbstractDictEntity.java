    package uz.ciasev.ubdd_service.entity.dict.ubdd;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UbddAbstractDictEntity extends AbstractExternalDictEntity {
}
