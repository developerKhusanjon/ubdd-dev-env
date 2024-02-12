package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractAliasedDict;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.PersonDocumentTypeDTOI;
import uz.ciasev.ubdd_service.utils.converter.CitizenshipCalculatingModeAliasAliasConverter;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.PersonDocumentTypeCacheDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "d_person_document_type")
@JsonDeserialize(using = PersonDocumentTypeCacheDeserializer.class)
public class PersonDocumentType extends AbstractAliasedDict<PersonDocumentTypeAlias> {

    @Getter
    private Boolean isBiometric;

    @Getter
    @Column(name = "citizenship_calculating_mode_id")
    @Convert(converter = CitizenshipCalculatingModeAliasAliasConverter.class)
    private CitizenshipCalculatingModeAlias citizenshipCalculatingMode;

    @Override
    public void construct(DictCreateDTOI request) {
        super.construct(request);
        this.isBiometric = false;
        this.citizenshipCalculatingMode = CitizenshipCalculatingModeAlias.CALCULATION_UNACCEPTABLE;
    }

    public void construct(PersonDocumentTypeDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(PersonDocumentTypeDTOI request) {
        super.construct(request);
        set(request);
    }

    private void set(PersonDocumentTypeDTOI request) {
        this.isBiometric = request.getIsBiometric();
        this.citizenshipCalculatingMode = request.getCitizenshipCalculatingMode().getAlias();
    }

    public boolean notBiometric() {
        return !isBiometric;
    }


    // todo каслыдь для работы создания потерпевшего судом
    public void setIsActive() {
        this.isActive = Boolean.TRUE;
    }

    @Override
    protected PersonDocumentTypeAlias getDefaultAliasValue() {
        return PersonDocumentTypeAlias.OTHER;
    }

    public Long getCitizenshipCalculatingModeId() {
        if (citizenshipCalculatingMode == null) return null;
        return citizenshipCalculatingMode.getId();
    }
}
