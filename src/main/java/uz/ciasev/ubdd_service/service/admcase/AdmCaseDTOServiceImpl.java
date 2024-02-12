package uz.ciasev.ubdd_service.service.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseDeletionRegistrationResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtCaseFieldsResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.violator.ViolatorRepository;
import uz.ciasev.ubdd_service.service.admcase.deletion.AdmCaseDeletionRegistrationService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.user.UserDTOService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdmCaseDTOServiceImpl implements AdmCaseDTOService {

    private final UserDTOService userDTOService;
    private final AdmCaseAccessService admCaseAccessService;
    private final CourtCaseFieldsService courtCaseFieldsService;
    private final ViolatorRepository violatorRepository;
    private final AdmCaseService admCaseService;
    private final AdmCaseDeletionRegistrationService deletionRegistrationService;

    @Override
    public AdmCaseDetailResponseDTO buildDetail(User user, Supplier<AdmCase> supplier) {
        AdmCase admCase = supplier.get();
        InspectorResponseDTO considerUser = Optional.ofNullable(admCase.getConsiderUserId())
                .map(userDTOService::findInspectorById)
                .orElse(null);

        List<ActionAlias> permittedActions = admCaseAccessService.calculatePermittedActions(user, admCase);

        CourtCaseFieldsResponseDTO courtCaseFieldsResponseDTO = courtCaseFieldsService.findByCaseId(admCase.getId()).map(this::convertToCourtFieldsDetail).orElse(null);

        AdmCaseDeletionRegistrationResponseDTO deletionRegistrationDTO = null;
        if (admCase.isDeleted()) {
            deletionRegistrationDTO = deletionRegistrationService.findLatestByAdmCase(admCase)
                    .map(AdmCaseDeletionRegistrationResponseDTO::new)
                    .orElse(null);
        }

        return new AdmCaseDetailResponseDTO(admCase, considerUser, permittedActions, courtCaseFieldsResponseDTO, deletionRegistrationDTO);
    }

    @Override
    public AdmCaseResponseDTO buildAdmCaseById(Long admCaseId) {
        AdmCase admCase = admCaseService.getById(admCaseId);

        return new AdmCaseResponseDTO(admCase);
    }

    @Override
    public CourtCaseFieldsResponseDTO buildCourtFieldsDetail(Supplier<CourtCaseFields> supplier) {
        return convertToCourtFieldsDetail(supplier.get());
    }

    @Override
    public Page<AdmCaseListResponseDTO> buildPage(Supplier<Page<AdmCaseListProjection>> supplier) {
        return supplier.get().map(AdmCaseListResponseDTO::new);
    }

    @Override
    public List<AdmCaseListResponseDTO> buildList(Supplier<List<AdmCaseListProjection>> supplier) {
        return supplier.get()
                .stream()
                .map(AdmCaseListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CourtCaseFieldsResponseDTO convertToCourtFieldsDetail(CourtCaseFields fields) {
        if (fields == null) {
            return null;
        }

        return new CourtCaseFieldsResponseDTO(fields,
                courtCaseFieldsService.findChancelleryData(fields),
                violatorRepository.findViolatorCourtReturnReasonProjectionByAdmCaseId(fields.getCaseId()));
    }
}
