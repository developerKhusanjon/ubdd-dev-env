package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.utils.FormatUtils;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DecisionDetailResponseDTO extends DecisionListResponseDTO {

    private final String mobile;
    private final String actualAddress;
    private final Long personDocumentTypeId;
    private final String documentSeries;
    private final String documentNumber;
    private final LocalDate documentGivenDate;
    private final LocalDate documentExpireDate;
    private final LocalFileUrl photoUrl;

    private final MibCardResponseDTO mibCard;

    private final List<String> permittedActions;

    public DecisionDetailResponseDTO(Decision decision,
                                     Violator violator,
                                     @Nullable ViolatorDetail violatorDetail,
                                     Address actualAddress,
                                     Person person,
                                     Resolution resolution,
                                     CancellationResolution cancellationResolution,
                                     PunishmentDetailResponseDTO mainPunishment,
                                     PunishmentDetailResponseDTO additionPunishment,
                                     MibCardResponseDTO mibCard,
                                     List<ActionAlias> permittedActions) {

        super(decision, violator, person, resolution, cancellationResolution, mainPunishment, additionPunishment);

        if (violatorDetail != null) {
            this.personDocumentTypeId = violatorDetail.getPersonDocumentTypeId();
            this.documentSeries = violatorDetail.getDocumentSeries();
            this.documentNumber = violatorDetail.getDocumentNumber();
            this.documentGivenDate = violatorDetail.getDocumentGivenDate();
            this.documentExpireDate = violatorDetail.getDocumentExpireDate();
        } else {
            this.personDocumentTypeId = null;
            this.documentSeries = null;
            this.documentNumber = null;
            this.documentGivenDate = null;
            this.documentExpireDate = null;
        }
        this.photoUrl = LocalFileUrl.ofNullable(violator.getPhotoUri());
        this.mobile = violator.getMobile();
        this.actualAddress = FormatUtils.addressToText(actualAddress);

        this.mibCard = mibCard;

        this.permittedActions = permittedActions.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}

