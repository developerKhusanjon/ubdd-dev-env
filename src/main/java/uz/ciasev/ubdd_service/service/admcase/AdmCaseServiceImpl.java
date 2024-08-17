package uz.ciasev.ubdd_service.service.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamNotPresent;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseRepository;
import uz.ciasev.ubdd_service.service.generator.AdmCaseNumberGeneratorService;
import uz.ciasev.ubdd_service.service.status.AdmStatusDictionaryService;

import java.time.LocalDate;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;

@Service
@RequiredArgsConstructor
public class AdmCaseServiceImpl implements AdmCaseService {

    private final AdmCaseRepository admCaseRepository;
    private final AdmStatusDictionaryService admStatusService;
    private final AdmCaseNumberGeneratorService admCaseNumberGeneratorService;

    @Override
    @Transactional
    public AdmCase createEmptyAdmCase(Inspector user, Organ organ, Department department, Region region, District district) {

        LocalDate openedDate = LocalDate.now();

        String acNumber = admCaseNumberGeneratorService.generateNumber(openedDate);
        String acSeries = admCaseNumberGeneratorService.generateSeries();
        AdmStatus admStatus = admStatusService.findByAlias(CONSIDERING);

        return admCaseRepository.save(AdmCase
                .builder()
                .openedDate(openedDate)
                .user(user.getUser())
                .series(acSeries)
                .number(acNumber)
                .organ(organ)
                .department(department)
                .region(region)
                .district(district)
                .status(admStatus)
                .build());
    }

    @Override
    public AdmCase createCourtCopyAdmCase(AdmCase admCase, Long claimId) {

        LocalDate openedDate = LocalDate.now();

        String acNumber = admCaseNumberGeneratorService.generateNumber(openedDate);
        String acSeries = admCaseNumberGeneratorService.generateSeries();

        AdmCase newAdmCase = admCase.toBuilder()
                .id(null)
                .openedDate(openedDate)
                .claimId(claimId)
                .createdTime(null)
                .editedTime(null)
                .series(acSeries)
                .number(acNumber)
                .mergedToAdmCaseId(null)
                .courtOutNumber("SUD-" + admCase.getOrgan().getCode() + "-" + acNumber)
                //todo отрефавторить
                .violators(null)
                .courtCaseFields(null)
                .resolutions(null)
                .build();

        return admCaseRepository.save(newAdmCase);
    }

    @Override
    public AdmCase createCopyAdmCase(User user, AdmCase admCase) {

        LocalDate openedDate = LocalDate.now();

        String acNumber = admCaseNumberGeneratorService.generateNumber(openedDate);
        String acSeries = admCaseNumberGeneratorService.generateSeries();

        AdmCase newAdmCase = AdmCase.builder()
                .user(user)
                .openedDate(openedDate)
                .considerUser(user)
                .considerInfo(user.getInfo())
                .series(acSeries)
                .number(acNumber)
                .organ(user.getOrgan())
                .department(user.getDepartment())
                .region(user.getRegion())
                .district(user.getDistrict())
                .consideredTime(admCase.getConsideredTime())
                .status(admStatusService.findByAlias(CONSIDERING))
                //todo отрефавторить
                .violators(null)
                .courtCaseFields(null)
                .resolutions(null)
                .build();

        return admCaseRepository.save(newAdmCase);
    }

    @Override
    @Transactional
    public AdmCase update(Long id, AdmCase admCase) {
        // TODO: 08.11.2023  
        admCase.setId(id);
        return admCaseRepository.saveAndFlush(admCase);
    }

    @Override
    public AdmCase getById(Long id) {
        return admCaseRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AdmCase.class, id));
    }

    @Override
    public AdmCase getByProtocolId(Long protocolId) {
        return admCaseRepository
                .findByProtocolId(protocolId)
                .orElseThrow(() -> new EntityByParamsNotFound(AdmCase.class, "protocolId", protocolId));
    }

    @Override
    public AdmCase getByViolator(Violator violator) {
        return admCaseRepository.findByViolatorId(violator.getId())
                .orElseThrow(() -> new EntityByParamsNotFound(AdmCase.class, "violatorId", violator.getId()));
    }

    @Override
    public AdmCase findByJuridic(Juridic juridic) {
        return admCaseRepository.findByJuridicId(juridic.getId())
                .orElseThrow(() -> new EntityByParamsNotFound(AdmCase.class, "juridic", juridic.getId()));
    }

    @Override
    public AdmCase getByIdAndClaimId(Long id, Long claimId) {
        return admCaseRepository.findByIdAndClaimId(id, claimId)
                .orElseThrow(() -> new EntityByParamNotPresent(AdmCase.class, "id", id,"claimId", claimId));
    }

    @Override
    public AdmCase getByClaimId(Long claimId) {
        return admCaseRepository.findByClaimId(claimId)
                .orElseThrow(() -> new EntityByParamNotPresent(AdmCase.class, "claimId", claimId));
    }
}
