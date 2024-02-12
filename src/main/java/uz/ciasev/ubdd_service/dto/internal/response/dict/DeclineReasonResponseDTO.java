package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.admcase.DeclineReason;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclineReasonResponseDTO {

    private Long id;
    private String code;
    private MultiLanguage name;
    private Boolean isActive;

    public DeclineReasonResponseDTO(DeclineReason declineReason) {
        this.id = declineReason.getId();
        this.name = declineReason.getName();
        this.code = declineReason.getCode();
        this.isActive = declineReason.getIsActive();
    }
}
