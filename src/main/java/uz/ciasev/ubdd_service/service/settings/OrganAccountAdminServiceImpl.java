package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.DictIdValueOrAllType;
import uz.ciasev.ubdd_service.dto.internal.request.DictIdListValueOrAllType;
import uz.ciasev.ubdd_service.dto.internal.request.settings.DistrictIdListType;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsHistoricAction;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.OrganAccountSettingAlreadyExistsException;
import uz.ciasev.ubdd_service.repository.settings.BankAccountRepository;
import uz.ciasev.ubdd_service.repository.settings.OrganBankAccountRepository;
import uz.ciasev.ubdd_service.service.history.OrganAccountAdminHistoryService;
import uz.ciasev.ubdd_service.specifications.OrganAccountSettingsSpecifications;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrganAccountAdminServiceImpl implements OrganAccountAdminService {

    private final OrganBankAccountRepository repository;
    private final BankAccountRepository bankAccountRepository;
    private final OrganAccountBuilder builder;
    private final OrganAccountService accountService;
    private final OrganAccountSettingsSpecifications specifications;
    private final OrganAccountAdminHistoryService historyService;

    @Override
    public List<OrganAccountSettings> findAccounts(User user, OrganAccountSettingsCreateRequestDTO requestDTO) {
        return repository.findAll(buildSpecification(requestDTO));
    }

    @Transactional
    @Override
    public List<OrganAccountSettings> createAccounts(User user, OrganAccountSettingsCreateRequestDTO requestDTO) {
        validateNoExists(requestDTO);

        List<OrganAccountSettingsCreateRequest> requestList = builder.build(requestDTO);

//        validateNoExists(requestList);

        BankAccount penaltyAccount = getOrCreateBankAccount(requestDTO.getPenaltyDepId());
        BankAccount compensationAccount = Optional.ofNullable(requestDTO.getCompensationDepId()).map(this::getOrCreateBankAccount).orElse(null);

        List<OrganAccountSettings> entityList = requestList.stream()
                .peek(r -> {
                    r.setPenaltyAccount(penaltyAccount);
                    r.setCompensationAccount(compensationAccount);
                })
                .map(OrganAccountSettings::new)
                .collect(Collectors.toList());

        historyService.register(user, OrganAccountSettingsHistoricAction.CREATE, entityList, requestDTO);

        return repository.saveAll(entityList);
    }

    @Transactional
    @Override
    public OrganAccountSettings update(User user, UUID id, OrganAccountSettingsUpdateRequestDTO requestDTO) {
        OrganAccountSettingsUpdateRequest updateRequest = getUpdateRequest(requestDTO);
        OrganAccountSettings entity = accountService.getById(id).setBankAccounts(updateRequest);

        repository.save(entity);
        historyService.register(user, OrganAccountSettingsHistoricAction.UPDATE, List.of(entity), requestDTO);

        return entity;
    }

    @Transactional
    @Override
    public void delete(User user, UUID id) {
        delete(user, List.of(id));
    }

    @Transactional
    @Override
    public void delete(User user, List<UUID> idList) {
        List<OrganAccountSettings> entityList = repository.findByIdIn(idList);
        repository.deleteByIdIn(idList);
        historyService.register(user, OrganAccountSettingsHistoricAction.DELETE, entityList, null);
    }

    private OrganAccountSettingsUpdateRequest getUpdateRequest(OrganAccountSettingsUpdateRequestDTO requestDTO) {
        BankAccount penaltyBankAccount = getOrCreateBankAccount(requestDTO.getPenaltyDepId());
        BankAccount compensationBankAccount = Optional.ofNullable(requestDTO.getCompensationDepId()).map(this::getOrCreateBankAccount).orElse(null);

        OrganAccountSettingsUpdateRequest updateRequest = new OrganAccountSettingsCreateRequest();
        updateRequest.setPenaltyAccount(penaltyBankAccount);
        updateRequest.setCompensationAccount(compensationBankAccount);

        return updateRequest;
    }

    private BankAccount getOrCreateBankAccount(Long billingId) {
        return bankAccountRepository.findByBillingId(billingId)
                .orElseGet(() -> bankAccountRepository.saveAndFlush(new BankAccount(null, billingId)));
    }

    private void validateNoExists(List<OrganAccountSettingsCreateRequest> newSettings) {
        Optional<OrganAccountSettingsCreateRequest> firstBadSetting = newSettings.stream()
                .filter(newSetting -> accountService.findByPlace(
                                newSetting,
                                newSetting.getArticlePart(),
                                newSetting.getBankAccountType().getId()
                        ).isPresent()
                ).parallel()
                .findAny();

        firstBadSetting.ifPresent(newSetting -> {
            throw new OrganAccountSettingAlreadyExistsException(
                    newSetting,
                    newSetting.getArticlePart(),
                    newSetting.getBankAccountType().getId()
            );
        });
    }

    public void validateNoExists(OrganAccountSettingsCreateRequestDTO requestDTO) {
        long existsCount = repository.count(buildSpecification(requestDTO));
        if (existsCount != 0) {
            throw new OrganAccountSettingAlreadyExistsException(existsCount);
        }
    }

    public Specification<OrganAccountSettings> buildSpecification(OrganAccountSettingsCreateRequestDTO requestDTO) {

        return SpecificationsCombiner.andAll(
                specifications.withOrganExactly(requestDTO.getOrgan()),
                specifications.withDepartmentExactly(requestDTO.getDepartment()),
                specifications.withBankAccountTypeId(requestDTO.getBankAccountType().getId()),
                buildSpecification(requestDTO.getRelatedArticlePart()),
                buildSpecification(requestDTO.getRelatedRegion()),
                buildSpecification(requestDTO.getRelatedDistrict())
        );
    }

    private Specification<OrganAccountSettings> buildSpecification(DictIdListValueOrAllType<ArticlePart> value) {
        if (value == null) return SpecificationsHelper.getEmpty();

        if (value.getIsAll()) {
            return specifications.withArticlePartExact(null);
        } else {
            return specifications.withArticlePartIn(value.getValue());
        }
    }

    private Specification<OrganAccountSettings> buildSpecification(DictIdValueOrAllType<Region> value) {
        if (value == null) return SpecificationsHelper.getEmpty();

        if (value.getIsAll()) {
            return SpecificationsHelper.getEmpty();
        }

        return specifications.withRegion(value.getValue());
    }

    private Specification<OrganAccountSettings> buildSpecification(DistrictIdListType relatedDistrict) {
        if (relatedDistrict == null) return SpecificationsHelper.getEmpty();

        if (relatedDistrict.getIsAll()) {
            if (!relatedDistrict.getIsHead()) {
                return Specification.not(specifications.withDistrictExactly(null));
            }
            return SpecificationsHelper.getEmpty();
        }

        Specification<OrganAccountSettings> districtFilter = specifications.withDistrictIn(relatedDistrict.getValue());

        if (relatedDistrict.getIsHead()) {
            districtFilter = districtFilter.or(specifications.withDistrictExactly(null));
        }

        return districtFilter;
    }
}
