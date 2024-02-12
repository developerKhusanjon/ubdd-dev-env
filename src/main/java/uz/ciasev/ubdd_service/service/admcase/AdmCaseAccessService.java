package uz.ciasev.ubdd_service.service.admcase;

import uz.ciasev.ubdd_service.config.base.GlobalConstants;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface AdmCaseAccessService {

    /**
     * Check access of user to Adm case.
     * User has access if user organ, department, region and district is empty or equals case field.
     */
    void checkAccessOnAdmCase(User user, AdmCase admCase);

    void checkAccessOnAdmCaseNoOrgan(User user, AdmCase admCase);

    /**
     * Check consider access of user to Adm case for prepare action.
     * Check pass if user is case consider(field considerUser) and case status permit action (see entity AdmStatusPermittedAction).
     */
    void checkConsiderActionWithAdmCase(User user, ActionAlias actionAlias, AdmCase admCase);

    /**
     * Check access of user to Adm case for prepare action.
     * Check pass if user organ, department, region and district is empty or equals case field
     * and case status permit action (see entity AdmStatusPermittedAction).
     */
    void checkAccessibleUserActionWithAdmCase(User user, ActionAlias actionAlias, AdmCase admCase);

    void checkPermitActionWithAdmCase(ActionAlias actionAlias, AdmCase admCase);

    List<ActionAlias> calculatePermittedActions(User user, AdmCase admCase);

    void checkConsiderOfAdmCase(User user, AdmCase admCase);

    boolean isConsiderOfAdmCase(User user, AdmCase admCase);

    boolean isHasNoAccessOnAdmCase(User user, AdmCase admCase);

    default boolean isMigrationActionAllow(AdmCase admCase) {
        if (!admCase.getOrgan().isGai()) {
            return false;
        }

        return admCase.getIsMigration() || admCase.getCreatedTime().toLocalDate().isBefore(GlobalConstants.DAY_OF_START_GAI_IN_PUBLIC_API);
    }
}
