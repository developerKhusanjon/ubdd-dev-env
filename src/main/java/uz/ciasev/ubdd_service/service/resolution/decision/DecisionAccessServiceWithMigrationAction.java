package uz.ciasev.ubdd_service.service.resolution.decision;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.status.AdmActionService;

import java.util.LinkedList;
import java.util.List;

@Service
public class DecisionAccessServiceWithMigrationAction extends DecisionAccessServiceImpl {

    protected final InvoiceService invoiceService;

    public DecisionAccessServiceWithMigrationAction(AdmActionService admActionService, AdmCaseAccessService admCaseAccessService, DecisionRepository decisionRepository, InvoiceService invoiceService) {
        super(admActionService, admCaseAccessService, decisionRepository);
        this.invoiceService = invoiceService;
    }

    @Override
    public List<ActionAlias> findDecisionPermittedActions(User user, Decision decision) {
        List<ActionAlias> actionAliases = super.findDecisionPermittedActions(user, decision);

        if (!admCaseAccessService.isMigrationActionAllow(decision.getResolution().getAdmCase())) {
            return actionAliases;
        }

        if (decision.getStatus().is(AdmStatusAlias.EXECUTED)) {
            return actionAliases;
        }

        if (decision.getDecisionTypeAlias().not(DecisionTypeAlias.PUNISHMENT)) {
            return actionAliases;
        }

        if (decision.getPenalty().isEmpty()) {
            return actionAliases;
        }

        if (invoiceService.findPenaltyInvoiceByDecision(decision).isPresent()) {
            return actionAliases;
        }


        LinkedList<ActionAlias> result = new LinkedList<>(actionAliases);
        result.add(ActionAlias.GAI_MIGRATION_ADD_INVOICE);
        return result;
    }
}