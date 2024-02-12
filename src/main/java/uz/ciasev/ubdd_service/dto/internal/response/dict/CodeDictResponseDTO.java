package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.DictEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CodeDictResponseDTO extends DictResponseDTO {


    public CodeDictResponseDTO(DictEntity entity) {
        super(entity);
    }
}
