package uz.ciasev.ubdd_service.entity.dict;

import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.AliasDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractAliasedDict<T extends Enum<T>> extends AbstractEmiDict implements AliasedDictEntity<T> {

    @Getter
    @Enumerated(EnumType.STRING)
    protected T alias;

    abstract protected T getDefaultAliasValue();

    @Override
    public void construct(DictCreateDTOI request) {
        super.construct(request);
        this.alias = getDefaultAliasValue();
    }

    public void construct(AliasDictCreateDTOI<T> request) {
        super.construct(request);
        this.alias = request.getAlias();
    }
}
