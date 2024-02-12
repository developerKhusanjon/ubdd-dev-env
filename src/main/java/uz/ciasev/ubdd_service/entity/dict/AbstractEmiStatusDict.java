package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictUpdateDTOI;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEmiStatusDict extends AbstractEmiDict {

    @Getter
    protected String color;

    public void construct(StatusDictCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(StatusDictUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(StatusDictUpdateDTOI request) {
        this.color = request.getColor();
    }
}
