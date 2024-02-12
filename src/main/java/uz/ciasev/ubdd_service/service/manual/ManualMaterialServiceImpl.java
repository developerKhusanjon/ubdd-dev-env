package uz.ciasev.ubdd_service.service.manual;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.LicenseReturningManualMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualCourtMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.PunishmentManualMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.TerminationManualMaterialDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.court.CourtLogService;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.court.CourtMaterialTypeService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialService;
import uz.ciasev.ubdd_service.service.document.DocumentService;
import uz.ciasev.ubdd_service.service.execution.CourtExecutionService;
import uz.ciasev.ubdd_service.service.generator.ManualMaterialClaimGeneratorService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;

import java.util.function.Function;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.*;
import static uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias.MANUAL_315_RESOLUTION;

@Service
@RequiredArgsConstructor
public class ManualMaterialServiceImpl implements ManualMaterialService {

    private final DecisionService decisionService;
    private final DecisionAccessService accessService;
    private final CourtExecutionService courtExecutionService;
    private final CourtMaterialService courtMaterialService;
    private final CourtLogService courtLogService;
    private final CourtMaterialService materialService;
    private final ManualMaterialClaimGeneratorService claimGeneratorService;
    private final CourtMaterialFieldsService fieldsService;
    private final CourtMaterialTypeService materialTypeService;

    private final AliasedDictionaryService<CourtStatus, CourtStatusAlias> courtStatusService;
    private final DocumentService documentService;
    private final AliasedDictionaryService<DocumentType, DocumentTypeAlias> documentTypeService;
    private final ManualMaterialResolutionService manualMaterialResolutionService;
    private final ResolutionActionService resolutionActionService;


    @Override
    @Transactional
    public Decision editPunishment(User user, Long decisionId, PunishmentManualMaterialDTO requestDTO) {
        return editDecision(
                user, decisionId, EDIT_PUNISHMENT_BY_MANUAL_MATERIAL, requestDTO.getMaterial(),
                decision -> manualMaterialResolutionService.editPunishment(decision, requestDTO)
        );
    }

    @Override
    @Transactional
    public Decision terminate(User user, Long decisionId, TerminationManualMaterialDTO requestDTO) {
        return editDecision(
                user, decisionId, TERMINATE_BY_MANUAL_MATERIAL, requestDTO.getMaterial(),
                decision -> manualMaterialResolutionService.terminate(decision, requestDTO)
        );
    }

    @Override
    @Transactional
    public Decision returnRevokedLicense(User user, Long decisionId, LicenseReturningManualMaterialDTO requestDTO) {
        Decision decision = decisionService.getById(decisionId);
        // Лишение прав - это всега только суд, а стандартная проверка запрещает доступ к решениям суда.
        accessService.checkAccess(user, decision);
        accessService.checkSystemActionPermit(RETURN_LICENSE_BY_MANUAL_MATERIAL, decision);
        validate(decision, requestDTO.getMaterial());

        Pair<CourtMaterial, CourtMaterialFields> materialData = createMaterial(user, decision, CourtMaterialTypeAlias.RETURN_DRIVING_LICENSE, requestDTO.getMaterial());
        CourtMaterialFields materialFields = materialData.getSecond();

        courtExecutionService.executionLicenseRevocation(decision, requestDTO.getMaterial().getHearingTime().toLocalDate());
        fieldsService.granted(materialFields);

        return decision;
    }


    private void validate(Decision decision, ManualCourtMaterialDTO materialDTO) {
        if (decision.getResolution().getResolutionTime().toLocalDate().isAfter(materialDTO.getRegistrationDate())) {
            throw new ValidationException(ErrorCode.REGISTRATION_DATE_LESS_THAN_RESOLUTION_TIME);
        }

        if (courtLogService.hasSearchRequest(decision)) {
            throw new ValidationException(ErrorCode.DECISION_HAS_COURT_SEARCH_REQUEST);
        }

        if (courtMaterialService.existsByDecisionId(decision.getId())) {
            throw new ValidationException(ErrorCode.DECISION_HAS_REAL_COURT_MATERIAL);
        }
    }

    private Decision editDecision(User user, Long decisionId, ActionAlias actionAlias, ManualCourtMaterialDTO materialDTO, Function<Decision, Decision> makeDecision) {
        Decision decision = getDecisionForAction(user, decisionId, actionAlias);
        validate(decision, materialDTO);

        Pair<CourtMaterial, CourtMaterialFields> materialData = createMaterial(user, decision, CourtMaterialTypeAlias.APPEAL_ON_ORGAN_DECISION, materialDTO);

        resolutionActionService.cancelResolutionByCourt(decision.getResolution(), ReasonCancellationAlias.COURT_REVIEW, null);
        Decision newDecision = makeDecision.apply(decision);

        materialService.resolved(materialData.getFirst());
        fieldsService.grantedWithNewResolution(materialData.getSecond(), newDecision.getResolution());

        return newDecision;
    }

    private Decision getDecisionForAction(User user, Long decisisonId, ActionAlias actionAlias) {
        Decision decision = decisionService.getById(decisisonId);
        accessService.checkUserActionPermit(user, actionAlias, decision);
        return decision;
    }

    private Pair<CourtMaterial, CourtMaterialFields> createMaterial(User user, Decision decision, CourtMaterialTypeAlias materialTypeAlias, ManualCourtMaterialDTO materialDTO) {

        materialDTO.setMaterialType(materialTypeService.getByAlias(materialTypeAlias));
        materialDTO.setCourtStatus(courtStatusService.getByAlias(CourtStatusAlias.RESOLVED));

        CourtMaterial material = materialService.create(claimGeneratorService.generateClaim(), decision);
        CourtMaterialFields fields = fieldsService.open(material, materialDTO);
        fieldsService.update(fields, materialDTO);


        attachDocumentToAdmCase(user, decision, materialDTO.getUri());

        return Pair.of(material, fields);
    }

    private void attachDocumentToAdmCase(User user, Decision decision, String uri) {
        DocumentRequestDTO requestDTO = new DocumentRequestDTO();
        requestDTO.setUri(uri);
        requestDTO.setDescription("РУЧНОЕ ВНЕСЕНИЕ РЕШЕНИЯ СУДА ПО 315");
        requestDTO.setDocumentType(documentTypeService.getByAlias(MANUAL_315_RESOLUTION));
        documentService.create(
                user,
                decision.getResolution().getAdmCase(),
                decision.getViolator().getPerson(),
                requestDTO
        );
    }
}
