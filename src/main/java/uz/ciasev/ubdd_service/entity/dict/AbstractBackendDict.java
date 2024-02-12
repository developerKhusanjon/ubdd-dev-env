package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ManualIdDictCreateDTOI;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractBackendDict<T extends Enum<T> & BackendAlias> extends AbstractManualIdDictEntity implements AliasedDictEntity<T> {

    @Getter
    @Enumerated(EnumType.STRING)
    protected T alias;

    public AbstractBackendDict(T alias) {
        super.constructBase(new ManualIdDictCreateDTOI() {
            public Long getId() {return alias.getId();}
            public MultiLanguage getName() {return defaultName;}
            public String getCode() {return String.valueOf(alias.getId());}
        });
        this.alias = alias;
    }

    public void update(DictUpdateDTOI request) {
        super.updateBase(request);
    }
}
