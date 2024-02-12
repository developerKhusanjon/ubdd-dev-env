package uz.ciasev.ubdd_service.service.resolution.decision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.*;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserListLiteResponseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionFullListProjection;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedDecisionDTO;
import uz.ciasev.ubdd_service.service.mib.MibCardDTOService;
import uz.ciasev.ubdd_service.service.mib.MibCardService;
import uz.ciasev.ubdd_service.service.resolution.cancellation.CancellationResolutionService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationDTOService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.search.decision.DecisionFullListResponseDTO;
import uz.ciasev.ubdd_service.service.user.SystemUserService;
import uz.ciasev.ubdd_service.service.user.UserService;
import uz.ciasev.ubdd_service.service.violator.ViolatorDetailService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionDTOServiceImpl implements DecisionDTOService {

    private final MibCardService mibCardService;
    private final MibCardDTOService mibDTOService;
    private final CancellationResolutionService cancellationResolutionService;
    private final DecisionAccessService decisionAccessService;
    private final UserService userService;
    private final SystemUserService systemUserService;
    private final AddressService addressService;
    private final CourtMaterialFieldsService courtMaterialFieldsService;
    private final ViolatorDetailService violatorDetailService;
    private final InvoiceService invoiceService;
    private final CompensationService compensationService;
    private final CompensationDTOService compensationDTOService;

    @Override
    public DecisionDetailResponseDTO buildDetailById(Supplier<Decision> supplier) {
        return convertToDTO(supplier.get());
    }

    @Override
    public List<DecisionListResponseDTO> buildList(Supplier<List<Decision>> supplier) {
        return supplier.get()
                .stream()
                .map(this::convertToListDTO)
                .collect(toList());
    }

    @Transactional
    @Override
    public DecisionListResponseDTO buildListForCreate(Supplier<CreatedDecisionDTO> supplier) {
        CreatedDecisionDTO data = supplier.get();

        DecisionListResponseDTO dto = convertToListDTO(data.getDecision());
        dto.setGovCompensation(data.getGovCompensation().map(compensationDTOService::convertToDTO).orElse(null));

        return dto;
    }

    @Override
    public Page<DecisionFullListResponseDTO> buildFullProjectionPage(Supplier<Page<DecisionFullListProjection>> supplier) {
        return supplier.get()
                .map(DecisionFullListResponseDTO::new);
    }

    @Override
    public List<DecisionFullListResponseDTO> buildFullProjectionList(Supplier<List<DecisionFullListProjection>> supplier) {
        return supplier.get()
                .stream()
                .map(DecisionFullListResponseDTO::new)
                .collect(toList());
    }

    @Override
    public List<CourtMaterialListResponseDTO> buildMaterialList(Supplier<List<CourtMaterial>> supplier) {
        return supplier.get().stream()
                .map(material -> {
                    CourtMaterialFields fields = courtMaterialFieldsService.getCurrentOpt(material).orElse(null);
                    return new CourtMaterialListResponseDTO(material, fields);
                }).collect(Collectors.toList());
    }

    private DecisionDetailResponseDTO convertToDTO(Decision decision) {
        if (decision == null) {
            return null;
        }

        MibCardResponseDTO mibExecutionCard = mibCardService.findByDecisionId(decision.getId()).map(mibDTOService::convertToDetailDTO).orElse(null);
        ViolatorDetail mainViolatorDetail = violatorDetailService.findMainByViolatorId(decision.getViolatorId()).orElse(null);

        CancellationResolution cancellationResolution = cancellationResolutionService
                .findByResolutionId(decision.getResolution().getId())
                .orElse(null);

        return new DecisionDetailResponseDTO(
                decision,
                decision.getViolator(),
                mainViolatorDetail,
                addressService.findById(decision.getViolator().getActualAddressId()),
                decision.getViolator().getPerson(),
                decision.getResolution(),
                cancellationResolution,
                Optional.ofNullable(decision.getMainPunishment()).map(this::convertToDetailDTO).orElse(null),
                Optional.ofNullable(decision.getAdditionPunishment()).map(this::convertToDetailDTO).orElse(null),
                mibExecutionCard,
                decisionAccessService.findDecisionPermittedActions(systemUserService.getCurrentUser(), decision)
        );
    }

    @Override
    public DecisionListResponseDTO convertToListDTO(Decision decision) {
        if (decision == null)
            return null;

        var cancellation = cancellationResolutionService
                .findByResolutionId(decision.getResolution().getId())
                .orElse(null);

        return new DecisionListResponseDTO(
                decision,
                decision.getViolator(),
                decision.getViolator().getPerson(),
                decision.getResolution(),
                cancellation,
                convertToListDTO(decision.getMainPunishment()),
                convertToListDTO(decision.getAdditionPunishment())
        );
    }


    @Override
    public PunishmentDetailResponseDTO convertToDetailDTO(Punishment punishment) {
        if (punishment == null)
            return null;

        Invoice invoice = null;
        if (punishment.getType().is(PunishmentTypeAlias.PENALTY)) {
            invoice = invoiceService.findByPenalty(punishment.getPenalty()).orElse(null);
        }

        return new PunishmentDetailResponseDTO(
                punishment,
                invoice,
                Optional.ofNullable(punishment.getExecutionUserId()).map(userService::findById).map(UserListLiteResponseDTO::new).orElse(null)
        );
    }


    @Override
    public PunishmentListResponseDTO convertToListDTO(Punishment punishment) {
        if (punishment == null) return null;

        Invoice invoice = null;
        if (punishment.getType().is(PunishmentTypeAlias.PENALTY)) {
            invoice = invoiceService.findByPenalty(punishment.getPenalty()).orElse(null);
        }

        return new PunishmentListResponseDTO(punishment, invoice);
    }


    @Override
    public ForceExecutionResponseDTO convertToListDTO(ForceExecution forceExecution) {
        if (forceExecution == null)
            return null;

        return new ForceExecutionResponseDTO(forceExecution);
    }

}
