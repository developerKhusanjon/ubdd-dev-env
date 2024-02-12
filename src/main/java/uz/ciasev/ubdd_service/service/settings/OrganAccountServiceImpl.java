package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.settings.OrganAccountSettingsResponseDTO;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.exception.BankInfoNotFoundException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.settings.OrganBankAccountRepository;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganAccountServiceImpl implements OrganAccountService {

    private final OrganBankAccountRepository repository;
    public final FilterHelper<OrganAccountSettings> filterHelper;

    @Override
    public OrganAccountSettings getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityByParamsNotFound(OrganAccountSettings.class, "id", id));
    }

    @Override
    public Optional<OrganAccountSettings> findByPlace(Place place, ArticlePart articlePart, Long bankAccountType) {
        return repository.findByArticlePartAndOrganAndDepartmentAndRegionAndDistrictAndBankAccountTypeId(
                articlePart,
                place.getOrgan(),
                place.getDepartment(),
                place.getRegion(),
                place.getDistrict(),
                bankAccountType
        );
    }

    @Override
    public OrganAccountSettings getByPlace(Place place, @Nullable ArticlePart articlePart, Long bankAccountType) {
        return findByPlace(place, articlePart, bankAccountType)
                .orElseThrow(() -> new BankInfoNotFoundException(place, articlePart, bankAccountType));
    }

    @Override
    public Page<OrganAccountSettings> findAll(Map<String, String> filters, Pageable pageable) {
        return repository.findAll(filterHelper.getParamsSpecification(filters), pageable);
    }

    @Override
    public OrganAccountSettingsResponseDTO buildDTO(OrganAccountSettings entity) {
        return new OrganAccountSettingsResponseDTO(entity);
    }
}
