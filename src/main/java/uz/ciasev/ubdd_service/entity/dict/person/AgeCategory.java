package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.AgeCategoryDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.AgeCategoryCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "d_age_category")
@NoArgsConstructor
@JsonDeserialize(using = AgeCategoryCacheDeserializer.class)
public class AgeCategory extends AbstractEmiDict {

    @Getter
    private int ageFrom;

    @Getter
    private int ageTo;

    @Getter
    @Enumerated(EnumType.STRING)
    @Deprecated
    private AgeCategoryOwnerAlias ownerAlias;

    @Getter
    private boolean isJuvenile;

    @Getter
    private boolean isViolatorOnly;

    public void construct(AgeCategoryDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(AgeCategoryDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(AgeCategoryDTOI request) {
        this.ageFrom = request.getAgeFrom();
        this.ageTo = request.getAgeTo();
        this.isJuvenile = request.getIsJuvenile();
        this.isViolatorOnly = request.getIsViolatorOnly();
        this.ownerAlias = AgeCategoryOwnerAlias.OTHER;
    }
}
