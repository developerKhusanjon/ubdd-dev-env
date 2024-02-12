package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import java.util.Collection;
import java.util.List;

import static uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName.*;

@Getter
@Setter
@RequiredArgsConstructor
public class InformationCourtAction implements CourtAction {

    private final CourtActionRequirementServices services;

    private final CourtStatus courtStatus;


    @Override
    public MaterialActionContext apply(MaterialActionContext context) {

        CourtMaterialFields newCourtFields = services.getCourtMaterialFieldsService().updateInformationStatus(context.getCourtFields(), courtStatus);

        return context.toBuilder()
                .courtFields(newCourtFields)
                .build();
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.UPDATE_INFORMATION_STATUS;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(REVIEW, SEPARATION, MERGE, MOVEMENT, RESOLUTION, RETURNING, EDITING_OF_RESOLUTION, EDITING_OF_RETURNING);
    }
}
