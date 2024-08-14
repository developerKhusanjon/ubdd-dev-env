package uz.ciasev.ubdd_service.entity.dict;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.dict.requests.ManualIdDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ManualIdDictUpdateDTOI;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractManualIdDictEntity extends AbstractDict {

    @Setter
    @Getter
    @Id
    protected Long id;

    protected void constructBase(ManualIdDictCreateDTOI request) {
        super.constructBase(request);
        this.id = request.getId();
    }

    public void updateBase(ManualIdDictUpdateDTOI request) {
        super.updateBase(request);
    }
}
