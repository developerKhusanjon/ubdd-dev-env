package uz.ciasev.ubdd_service.dto.internal.response.dict.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.system.MessageLocalization;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageLocalizationResponseDTO {

    private Long id;
    private String code;
    private String fieldName;
    private MultiLanguage text;

    public MessageLocalizationResponseDTO(MessageLocalization messageLocalization) {
        this.id = messageLocalization.getId();
        this.code = messageLocalization.getCode();
        this.text = messageLocalization.getText();
        this.fieldName = messageLocalization.getFieldName();
    }
}
