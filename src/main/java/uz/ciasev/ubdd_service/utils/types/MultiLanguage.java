package uz.ciasev.ubdd_service.utils.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiLanguage implements Serializable {

    @NotNull(message = "RU_CANT_BE_EMPTY")
    private String ru;

    @NotNull(message = "KIR_CANT_BE_EMPTY")
    private String kir;

    @NotNull(message = "LAT_CANT_BE_EMPTY")
    private String lat;

    public static enum Language {
        RU, KIR, LAT;
    }

    public String get(Language language) {
        switch (language) {
            case RU: return ru;
            case KIR: return kir;
            case LAT: return lat;
            default: throw new ImplementationException(String.format("Unknown language %s", language));
        }
    }
}
