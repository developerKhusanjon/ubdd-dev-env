package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganInfoCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganInfoUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.settings.OrganInfoRepository;

@Service
@RequiredArgsConstructor
public class OrganInfoCreateUpdateServiceImpl implements OrganInfoCreateUpdateService {

    private final OrganInfoRepository repository;
    private final OrganInfoSearchService organInfoSearchService;

    @Override
    @Transactional
    public OrganInfo create(OrganInfoCreateRequestDTO requestDTO) {
        if (organInfoSearchService.findByPlace(requestDTO).isPresent()) {
            throw new ValidationException(ErrorCode.ORGAN_INFO_FOR_GEOGRAPHY_ALREADY_EXISTS);
        }

        OrganInfo organInfo = new OrganInfo(
                requestDTO.getOrgan(),
                requestDTO.getDepartment(),
                requestDTO.getRegion(),
                requestDTO.getDistrict(),
                requestDTO
        );
        repository.save(organInfo);
        return organInfo;
    }

    @Override
    @Transactional
    public void update(Long id, OrganInfoUpdateRequestDTO requestDTO) {
        OrganInfo organInfo = organInfoSearchService.getById(id);
        organInfo.setDescriptiveFields(requestDTO);
        repository.save(organInfo);
    }
}
