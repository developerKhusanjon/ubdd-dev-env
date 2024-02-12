package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserListLiteResponseDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import javax.annotation.Nullable;

@Getter
public class PunishmentDetailResponseDTO extends PunishmentListResponseDTO {

    private final UserListLiteResponseDTO executionUser;

    public PunishmentDetailResponseDTO(Punishment punishment, @Nullable Invoice invoice, UserListLiteResponseDTO executionUser) {
        super(punishment, invoice);
        this.executionUser = executionUser;
    }
}

