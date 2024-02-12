package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.CourtMaterialTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "court_material_type")
@NoArgsConstructor
@JsonDeserialize(using = CourtMaterialTypeCacheDeserializer.class)
public class CourtMaterialType extends CourtAliasedAbstractDictEntity<CourtMaterialTypeAlias> {

    @Getter
    @Enumerated(EnumType.STRING)
    private CourtMaterialGroupAlias groupAlias;

    public boolean is(CourtMaterialGroupAlias o) {
        return o.equals(groupAlias);
    }

    public boolean not(CourtMaterialGroupAlias o) {
        return !o.equals(groupAlias);
    }

    public final boolean oneOf(CourtMaterialGroupAlias... os) {
        for (CourtMaterialGroupAlias o : os) {
            if (o.equals(groupAlias)) {
                return true;
            }
        }

        return false;
    }

    public final boolean notOneOf(CourtMaterialGroupAlias... os) {
        return !oneOf(os);
    }
}
