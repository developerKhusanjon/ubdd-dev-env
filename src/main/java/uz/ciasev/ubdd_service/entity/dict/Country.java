package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.CountryCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_country")
@NoArgsConstructor
@JsonDeserialize(using = CountryCacheDeserializer.class)
public class Country extends AbstractEmiDict {

    public static boolean isUzbekistan(Long id) {
        return Long.valueOf(1).equals(id);
    }

    public boolean isUzbekistan() {
        return Country.isUzbekistan(getId());
    }
}
