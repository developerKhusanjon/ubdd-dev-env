package uz.ciasev.ubdd_service.repository.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.specifications.ProtocolSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProtocolFilterBean {

    private final ProtocolSpecifications protocolSpecifications;

    private List<Pair<String, Filter<Protocol>>> getBasePairs() {
        return List.of(
                Pair.of("pinpp", new StringFilter<Protocol>(protocolSpecifications::withViolatorPinpp)),
                Pair.of("firstName", new StringFilter<Protocol>(protocolSpecifications::withViolatorFirstNameLike)),
                Pair.of("secondName", new StringFilter<Protocol>(protocolSpecifications::withViolatorSecondNameLike)),
                Pair.of("lastName", new StringFilter<Protocol>(protocolSpecifications::withViolatorLastNameLike)),
                Pair.of("birthDate", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirth)),
                Pair.of("birthDateFrom", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirthAfter)),
                Pair.of("birthDateTo", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirthBefore)),
                Pair.of("nationalityId", new LongFilter<Protocol>(protocolSpecifications::withViolatorNationalityId)),
                Pair.of("genderId", new LongFilter<Protocol>(protocolSpecifications::withViolatorGenderId)),
                Pair.of("countryId", new LongFilter<Protocol>(protocolSpecifications::withViolatorBirthCountryId)),
                Pair.of("isMain", new BooleanFilter<Protocol>(protocolSpecifications::withIsMain)),

//                Pair.of("isArchived", new DefaultValueBooleanFilter<>(false, protocolSpecifications::withIsArchived)),

                Pair.of("personDocumentTypeId", new LongFilter<Protocol>(protocolSpecifications::withViolatorDocumentTypeId)),
                Pair.of("documentSeries", new StringFilter<Protocol>(protocolSpecifications::withViolatorDocumentSeries)),
                Pair.of("documentNumber", new StringFilter<Protocol>(protocolSpecifications::withViolatorDocumentNumber)),

                Pair.of("violatorActualAddressRegionId", new LongFilter<Protocol>(protocolSpecifications::withViolatorAddressRegionId)),
                Pair.of("violatorActualAddressDistrictId", new LongFilter<Protocol>(protocolSpecifications::withViolatorAddressDistrictId)),
                Pair.of("violatorActualAddressCountryId", new LongFilter<Protocol>(protocolSpecifications::withViolatorActualCountryId)),

                Pair.of("userId", new LongFilter<Protocol>(protocolSpecifications::withUserId)),
                Pair.of("userWorkCertificate", new StringFilter<Protocol>(protocolSpecifications::withUserWorkCertificate)),
                Pair.of("protocolNumber", new StringFilter<Protocol>(protocolSpecifications::withNumber)),
                Pair.of("protocolSeries", new StringFilter<Protocol>(protocolSpecifications::withSeries)),
                Pair.of("oldSeries", new StringFilter<Protocol>(protocolSpecifications::withOldSeries)),
                Pair.of("oldNumber", new StringFilter<Protocol>(protocolSpecifications::withOldNumber)),
                Pair.of("createdTimeFrom", new DateFilter<Protocol>(protocolSpecifications::createdAfter)),
                Pair.of("createdTimeTo", new DateFilter<Protocol>(protocolSpecifications::createdBefore)),
                Pair.of("violationTimeFrom", new DateFilter<Protocol>(protocolSpecifications::violationTimeAfter)),
                Pair.of("violationTimeTo", new DateFilter<Protocol>(protocolSpecifications::violationTimeBefore)),
                Pair.of("registrationTimeFrom", new DateFilter<Protocol>(protocolSpecifications::registeredAfter)),
                Pair.of("registrationTimeTo", new DateFilter<Protocol>(protocolSpecifications::registeredBefore)),
                Pair.of("articleId", new LongFilter<Protocol>(protocolSpecifications::withArticleId)),
                Pair.of("articlePartId", new LongFilter<Protocol>(protocolSpecifications::withArticlePartId)),
                Pair.of("articleViolationTypeId", new LongFilter<Protocol>(protocolSpecifications::withArticleViolationTypeId)),
                Pair.of("organId", new LongFilter<Protocol>(protocolSpecifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<Protocol>(protocolSpecifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<Protocol>(protocolSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<Protocol>(protocolSpecifications::withDistrictId)),
                Pair.of("isJuridic", new BooleanFilter<Protocol>(protocolSpecifications::withIsJuridic)),

                Pair.of("carRegNum", new StringFilter<Protocol>(protocolSpecifications::withUbddVehicleNumberLike)),
                Pair.of("vehicleNumber", new StringFilter<Protocol>(protocolSpecifications::withUbddVehicleNumberLike)),

                Pair.of("ubddVehicleArrestId", new LongFilter<Protocol>(protocolSpecifications::withUbddVehicleArrestId)),

                Pair.of("admCaseStatusId", new LongFilter<Protocol>(protocolSpecifications::withAdmCaseStatusId)),

                Pair.of("hasActiveResolution", new BooleanFilter<>(protocolSpecifications::hasActiveResolution)),
                Pair.of("resolutionTimeFrom", new DateFilter<Protocol>(protocolSpecifications::resolvedAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<Protocol>(protocolSpecifications::resolvedBefore)),
                Pair.of("decisionInvoiceSerial", new StringFilter<Protocol>(protocolSpecifications::withDecisionInvoiceSerial)),
                Pair.of("decisionNumber", new StringFilter<Protocol>(protocolSpecifications::withDecisionNumber)),
                Pair.of("exceptTermination", new BooleanFilter<Protocol>(protocolSpecifications::exceptTerminationByParticipateOfRepeatability))
        );
    }

    @Bean
    @Primary
    public RequiredFilterHelper<Protocol> getProtocolFilterHelper() {
        List<Pair<String, Filter<Protocol>>> filters = new ArrayList<>(getBasePairs());
        filters.add(Pair.of("isArchived", new DefaultValueBooleanFilter<>(false, protocolSpecifications::withIsArchived)));

        return new RequiredFilterHelper<Protocol>(filters);
    }

    @Bean("coreApiProtocolFilterHelper")
    public RequiredFilterHelper<Protocol> getProtocolFilterHelperForCoreApi() {

        List<Pair<String, Filter<Protocol>>> filters = new ArrayList<>(getBasePairs());
        filters.add(Pair.of("isDeleted", new BooleanFilter<>(protocolSpecifications::withIsDeleted)));
        filters.add(Pair.of("isArchived", new BooleanFilter<>(protocolSpecifications::withIsArchived)));

        return new RequiredFilterHelper<Protocol>(filters);
    }

    @Bean("participantProtocolFilterHelper")
    public RequiredFilterHelper<Protocol> getParticipantProtocolFilterHelper() {
        List<Pair<String, Filter<Protocol>>> filters = new ArrayList<>(List.of(
                Pair.of("pinpp", new StringFilter<Protocol>(protocolSpecifications::withViolatorPinpp)),
                Pair.of("firstName", new StringFilter<Protocol>(protocolSpecifications::withViolatorFirstNameLike)),
                Pair.of("secondName", new StringFilter<Protocol>(protocolSpecifications::withViolatorSecondNameLike)),
                Pair.of("lastName", new StringFilter<Protocol>(protocolSpecifications::withViolatorLastNameLike)),
                Pair.of("birthDate", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirth)),
                Pair.of("birthDateFrom", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirthAfter)),
                Pair.of("birthDateTo", new DateFilter<Protocol>(protocolSpecifications::withViolatorBirthBefore)),

                Pair.of("documentSeries", new StringFilter<Protocol>(protocolSpecifications::withViolatorDocumentSeries)),
                Pair.of("documentNumber", new StringFilter<Protocol>(protocolSpecifications::withViolatorDocumentNumber)),
                Pair.of("isArchived", new DefaultValueBooleanFilter<>(false, protocolSpecifications::withIsArchived))
        ));
        return new RequiredFilterHelper<Protocol>(filters);
    }
}

