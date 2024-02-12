package uz.ciasev.ubdd_service.service.admcase;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtCaseFieldsResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.function.Supplier;

public interface AdmCaseDTOService {

    AdmCaseDetailResponseDTO buildDetail(User user, Supplier<AdmCase> supplier);

    AdmCaseResponseDTO buildAdmCaseById(Long admCaseId);

    CourtCaseFieldsResponseDTO buildCourtFieldsDetail(Supplier<CourtCaseFields> supplier);

    Page<AdmCaseListResponseDTO> buildPage(Supplier<Page<AdmCaseListProjection>> supplier);

    List<AdmCaseListResponseDTO> buildList(Supplier<List<AdmCaseListProjection>> supplier);

    CourtCaseFieldsResponseDTO convertToCourtFieldsDetail(CourtCaseFields fields);

}
