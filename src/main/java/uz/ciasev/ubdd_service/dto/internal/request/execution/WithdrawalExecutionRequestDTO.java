package uz.ciasev.ubdd_service.dto.internal.request.execution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.WithdrawalPunishment;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class WithdrawalExecutionRequestDTO implements PunishmentExecutionRequestDTO {

    @NotNull(message = ErrorCode.SALE_DATE_REQUIRED)
    private LocalDate saleDate;

    @NotNull(message = ErrorCode.SALE_AMOUNT_REQUIRED)
    private Long saleAmount;

    @NotNull(message = ErrorCode.SALE_ITEMS_REQUIRED)
    @Size(max = 500, message = ErrorCode.SALE_ITEMS_MAX_LENGTH)
    private String saleItems;

    private LocalDate repaymentDate;

    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    private String uri;

    @Override
    public Punishment applyTo(Punishment punishment) {

        WithdrawalPunishment withdrawalPunishment = punishment.getWithdrawal();

        withdrawalPunishment.setSaleDate(this.saleDate);
        withdrawalPunishment.setSaleAmount(this.saleAmount);
        withdrawalPunishment.setSaleItems(this.saleItems);
        withdrawalPunishment.setRepaymentDate(this.repaymentDate);

        punishment.setExecutionUri(this.uri);

        return punishment;
    }

    @Override
    public LocalDate getExecutionDate() {
        return repaymentDate;
    }

    @Override
    public PunishmentTypeAlias getPunishmentTypeAlias() {
        return PunishmentTypeAlias.WITHDRAWAL;
    }
}
