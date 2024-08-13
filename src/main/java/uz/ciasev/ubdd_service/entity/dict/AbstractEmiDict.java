package uz.ciasev.ubdd_service.entity.dict;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEmiDict extends AbstractDict {

    public AbstractEmiDict(Long id) {
        this.id = id;
    }

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public void construct(DictCreateDTOI request) {
        super.constructBase(request);
    }

    public void update(DictUpdateDTOI request) {
        super.updateBase(request);
    }
}