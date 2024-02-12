package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsRequest;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationCourtAction implements CourtAction, CourtMaterialFieldsRequest {

    private final CourtActionRequirementServices services;

    private final CourtMaterialType materialType;
    private final CourtStatus courtStatus;
    private final Region region;
    private final District district;
    private final Long instance;
    private final String judgeInfo;
    private final LocalDateTime hearingTime;
    private final String caseNumber;
    private final Boolean isProtest;
    private final Boolean isVccUsed;

    private final List<CourtViolatorData> violatorsData;


    @Override
    public MaterialActionContext apply(MaterialActionContext context) {

        CourtMaterialFields newCourtFields = saveCourtFields(context.getCourtFields());
        List<CourtMaterialDecision> newMaterialDecisions = saveViolatorData(context.getMaterialDecisions());

        //        eventDataService.setCourtDTOForFields(courtEventHolder.getCurrentInstance(), newCourtFields);

        return context.toBuilder()
                .courtFields(newCourtFields)
                .materialDecisions(newMaterialDecisions)
                .build();
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.UPDATE_CASE;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of();
    }

    private CourtMaterialFields saveCourtFields(CourtMaterialFields courtFields) {

        return services.getCourtMaterialFieldsService().update(courtFields, this);
    }

    private List<CourtMaterialDecision> saveViolatorData(List<CourtMaterialDecision> materialDecisions) {
        Map<Long, CourtViolatorData> violatorsDataMap = violatorsData.stream().collect(Collectors.toMap(CourtViolatorData::getViolatorId, d -> d));

        for (CourtMaterialDecision materialDecision : materialDecisions) {

            CourtViolatorData violatorData = violatorsDataMap.get(materialDecision.getDecision().getViolatorId());
            if (violatorData == null) {
                continue;
            }

            materialDecision.setDefendantId(violatorData.getDefendantId());
            materialDecision.setProsecutorRegion(violatorData.getProsecutorRegion());
            materialDecision.setProsecutorDistrict(violatorData.getProsecutorDistrict());
            materialDecision.setParticipated(violatorData.isParticipated());

            services.getCourtMaterialDecisionRepository().save(materialDecision);

        }

        return materialDecisions;
    }

    @Getter
    @RequiredArgsConstructor
    public static class CourtViolatorData {
        private final Long violatorId;
        private final Long defendantId;
        private final boolean isParticipated;
        private final Region prosecutorRegion;
        private final District prosecutorDistrict;
    }
}
