package uz.ciasev.ubdd_service.mvd_core.citizenapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class MultiLanguageResponseDTO implements Serializable {

    private String ru;

    private String kir;

    private String lat;

    public MultiLanguageResponseDTO(MultiLanguage multiLanguage) {
        this.ru = multiLanguage.getRu();
        this.kir = multiLanguage.getKir();
        this.lat = multiLanguage.getLat();
    }

    public static MultiLanguageResponseDTO ofNullable(AbstractDict genericAbstractEntity) {
        if (genericAbstractEntity == null) {
            return null;
        }

        return new MultiLanguageResponseDTO(genericAbstractEntity.getName());
    }

}
