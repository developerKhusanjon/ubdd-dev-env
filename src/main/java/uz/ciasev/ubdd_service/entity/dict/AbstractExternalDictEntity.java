package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictUpdateDTOI;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractExternalDictEntity extends AbstractManualIdDictEntity {

    public void construct(ExternalDictCreateDTOI request) {
        super.constructBase(request);
    }

    public void update(ExternalDictUpdateDTOI request) {
        super.updateBase(request);
    }
}
