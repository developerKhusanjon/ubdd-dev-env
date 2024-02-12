package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenNoteDTO;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassDataAbstract;
import uz.ciasev.ubdd_service.exception.autocon.AutoconRequiresVehicleNumberException;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.autocon.AutoconTransferService;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.service.dict.article.ArticleViolationTypeDictionaryService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolUbddDataService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutoconApiDTOServiceImpl implements AutoconApiDTOService {

    private final PunishmentService punishmentService;
    private final ProtocolUbddDataService ubddDataService;
    private final ProtocolService protocolService;
    private final AutoconTransferService transferService;
    private final ArticlePartDictionaryService articlePartService;
    private final ArticleViolationTypeDictionaryService articleViolationTypeService;

    @Override
    public AutoconOpenDTO buildOpenDTO(AutoconSending sending) {
        Decision decision = punishmentService.getDecisionByPunishmentId(sending.getPunishmentId());
        Punishment punishment = decision.getMainPunishment();
        if (!sending.getPunishmentId().equals(decision.getMainPunishmentId())) {
            throw new LogicalException("Autocon punishment and decision main punishment not equals");
        }

        Resolution resolution = decision.getResolution();
        Protocol protocol = protocolService.getMainByViolatorId(decision.getViolatorId());
        String vehicleNumber = protocol.getVehicleNumber();
        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new AutoconRequiresVehicleNumberException(vehicleNumber);
        }

        Optional<ProtocolUbddTexPassData> ubddTexPassDataOpt = ubddDataService.findTexPassByProtocolId(protocol.getId());


        AutoconOpenNoteDTO noteDTO = new AutoconOpenNoteDTO();
        if (ubddTexPassDataOpt.isPresent()) {
            UbddTexPassDataAbstract ubddTexPassData = ubddTexPassDataOpt.get();
            noteDTO.setOwner(ubddTexPassData.getOwnerInfo());
            noteDTO.setModel(ubddTexPassData.getVehicleModel());
        }

        noteDTO.setDateTime(resolution.getResolutionTime());
        noteDTO.setDecisionSeriesNumber(decision.getSeries() + decision.getNumber());
        noteDTO.setAmount(punishment.getAmount().toString());
        noteDTO.setArticleName(buildReason(decision, protocol));
        noteDTO.setRegionId(transferService.getExternalId(resolution.getRegion()));

        AutoconOpenDTO requestBody = new AutoconOpenDTO(sending, vehicleNumber, noteDTO);

        return requestBody;
    }

    @Override
    public AutoconCloseDTO buildCloseDTO(AutoconSending sending) {
        return new AutoconCloseDTO(sending);
    }

    private String buildReason(Decision decision, Protocol protocol) {
        ArticlePart articlePart;
        ArticleViolationType violationType;

        if (decision.getArticlePartId() != null) {
            articlePart = articlePartService.getById(decision.getArticlePartId());
            violationType = Optional.ofNullable(decision.getArticleViolationTypeId()).map(articleViolationTypeService::getById).orElse(null);
        } else {
            articlePart = protocol.getArticlePart();
            violationType = protocol.getArticleViolationType();
        }

        if (violationType == null) {
            return articlePart.getDefaultName();
        }

        return violationType.getDefaultName();
//        return String.join(" ", violationType.getDefaultName(), articlePart.getDefaultName());
    }
}
