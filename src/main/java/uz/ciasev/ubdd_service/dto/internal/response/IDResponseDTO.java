package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@AllArgsConstructor
public class IDResponseDTO {

    private Long id;

    public static IDResponseDTO of(AdmEntity entity) {
        return new IDResponseDTO(entity.getId());
    }
}
