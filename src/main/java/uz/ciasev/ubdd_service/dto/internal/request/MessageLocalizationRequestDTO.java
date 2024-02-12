package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.system.MessageLocalization;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageLocalizationRequestDTO {

    @Valid
    @ValidMultiLanguage
    private MultiLanguage text;

    public MessageLocalization buildMessageLocalization() {
        MessageLocalization messageLocalization = new MessageLocalization();
        messageLocalization.setText(this.text);

        return messageLocalization;
    }
}
