package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalStatusDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalStatusDictUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictUpdateDTOI;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractExternalStatusDictEntity extends AbstractManualIdDictEntity {

    @Getter
    protected String color;

    public void construct(ExternalStatusDictCreateDTOI request) {
        super.constructBase(request);
        set(request);
    }

    public void update(ExternalStatusDictUpdateDTOI request) {
        super.updateBase(request);
        set(request);
    }

    private void set(StatusDictUpdateDTOI request) {
        this.color = request.getColor();
    }
}
