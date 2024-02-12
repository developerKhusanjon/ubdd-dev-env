package uz.ciasev.ubdd_service.service.admcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.status.AdmActionService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import java.util.LinkedList;
import java.util.List;

@Service
public class AdmCaseAccessServiceWithMigrationAction extends AdmCaseAccessServiceImpl {

    @Autowired
    public AdmCaseAccessServiceWithMigrationAction(ValidationService validationService, AdmActionService admActionService) {
        super(validationService, admActionService);
    }

    @Override
    public List<ActionAlias> calculatePermittedActions(User user, AdmCase admCase) {
        List<ActionAlias> actionAliases = super.calculatePermittedActions(user, admCase);

        if (!isMigrationActionAllow(admCase)) {
            return actionAliases;
        }

        if (admCase.getStatus().notOneOf(AdmStatusAlias.CONSIDERING, AdmStatusAlias.REGISTERED)) {
            return actionAliases;
        }

        LinkedList<ActionAlias> result = new LinkedList<>(actionAliases);
        result.add(ActionAlias.GAI_MIGRATION_ADD_RESOLUTION);
        return result;
    }

}