package uz.ciasev.ubdd_service.service.trans.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.trans.response.mail.MailTransGeographyResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.trans.request.mail.MailTransGeographyCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;
import uz.ciasev.ubdd_service.exception.EntityByParamsAlreadyExists;
import uz.ciasev.ubdd_service.repository.trans.MailTransGeographyAdminRepository;
import uz.ciasev.ubdd_service.service.trans.AbstractTransEntityCRDService;

@Getter
@RequiredArgsConstructor
@Service
public class MailTransGeographyAdminServiceImpl extends AbstractTransEntityCRDService<MailTransGeography, MailTransGeographyCreateRequestDTO>
        implements MailTransGeographyAdminService {

    private final MailTransGeographyAdminRepository repository;
    private final String subPath = "mail/geography";
    private final Class<MailTransGeography> entityClass = MailTransGeography.class;

    private final TypeReference<MailTransGeographyCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};

    @Override
    @Transactional
    public MailTransGeography create(MailTransGeographyCreateRequestDTO requestDTO) {
        validate(requestDTO);

        return super.create(requestDTO);
    }

    @Override
    public Object buildListResponseDTO(MailTransGeography entity) {
        return new MailTransGeographyResponseDTO(entity);
    }

    private void validate(MailTransGeographyCreateRequestDTO requestDTO) {
        Region region = requestDTO.getRegion();
        District district = requestDTO.getDistrict();

        if (repository.existsByRegionAndDistrict(region, district)) {
            throw new EntityByParamsAlreadyExists(entityClass,
                                                  "regionId", region.getId(),
                                                  "districtId", district.getId());
        }
    }
}
