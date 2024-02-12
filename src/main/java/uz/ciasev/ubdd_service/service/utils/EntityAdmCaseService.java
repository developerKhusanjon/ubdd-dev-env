package uz.ciasev.ubdd_service.service.utils;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import java.util.List;

public interface EntityAdmCaseService {

    Long idBy(Punishment punishment);
    AdmCase by(Punishment punishment);
    AdmCase by(Compensation compensation);
    Long idBy(Compensation compensation);
    AdmCase by(MibCardMovement movement);
    AdmCase by(MibExecutionCard card);
    AdmCase by(Invoice invoice);
    List<AdmCase> byProtocolsId(List<Long> protocolsId);
}
