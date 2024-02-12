package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictUpdateDTOI;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractBackendStatusDict<T extends Enum<T> & BackendAlias> extends AbstractBackendDict<T> {

    @Getter
    protected String color;

    public void update(StatusDictUpdateDTOI request) {
        super.update(request);
        this.color = request.getColor();
    }
}
