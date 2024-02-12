package uz.ciasev.ubdd_service.service.utils;


import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

public interface AdmCaseOrganService {

    Organ by(Invoice invoice);

    Organ by(MibCardMovement card);

    Organ by(MibExecutionCard card);

    Organ by(Decision decision);

    Organ by(Punishment punishment);

    Organ by(Compensation compensation);

    Organ byAdmCaseId(Long caseId);
}
