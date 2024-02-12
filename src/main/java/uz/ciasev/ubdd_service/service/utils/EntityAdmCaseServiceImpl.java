package uz.ciasev.ubdd_service.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntityAdmCaseServiceImpl implements EntityAdmCaseService {

    private final PunishmentRepository punishmentRepository;
    private final CompensationRepository compensationRepository;
    private final MibExecutionCardRepository mibExecutionCardRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProtocolRepository protocolRepository;

    @Override
    public Long idBy(Punishment punishment) {
        return punishmentRepository.findAdmCaseIdByPunishment(punishment);
    }

    @Override
    public AdmCase by(Punishment punishment) {
        return punishmentRepository.findAdmCaseByPunishment(punishment);
    }

    @Override
    public AdmCase by(Compensation compensation) {
        return compensationRepository.findAdmCaseByCompensation(compensation);
    }

    @Override
    public Long idBy(Compensation compensation) {
        return compensationRepository.findAdmCaseIdByCompensation(compensation);
    }

    @Override
    public AdmCase by(MibCardMovement movement) {
        return by(movement.getCard());
    }

    @Override
    public AdmCase by(MibExecutionCard card) {

        Optional<AdmCase> opt;

        switch (card.getOwnerTypeAlias()) {
            case DECISION: {
                opt = mibExecutionCardRepository.findAdmCaseByDecisionCard(card);
                break;
            }
            case COMPENSATION: {
                opt = mibExecutionCardRepository.findAdmCaseByCompensationCard(card);
                break;
            }
            case EVIDENCE_DECISION: {
                opt = mibExecutionCardRepository.findAdmCaseByEvidenceDecisionCard(card, PageUtils.one()).stream().findFirst();
                break;
            }
            default:
                throw new NotImplementedException("GET MIB-CARD ADM-CASE NOT IMPLEMENTED FOR " + card.getOwnerTypeAlias());
        }

        return opt.orElseThrow(() -> new EntityByParamsNotFound(AdmCase.class, "mibCard", card.getId()));
    }

    @Override
    public AdmCase by(Invoice invoice) {

        Optional<AdmCase> opt;

        switch (invoice.getOwnerTypeAlias()) {
            case PENALTY: {
                opt = invoiceRepository.findAdmCaseByInvoiceInPenaltyPunishment(invoice);
                break;
            }
            case COMPENSATION: {
                opt = invoiceRepository.findAdmCaseByInvoiceInCompensation(invoice);
                break;
            }
            default:
                throw new NotImplementedException("GET INVOICE ADM-CASE NOT IMPLEMENTED FOR " + invoice.getOwnerTypeAlias());
        }

        return opt.orElseThrow(() -> new EntityByParamsNotFound(AdmCase.class, "invoice", invoice.getId()));
    }

    @Override
    public List<AdmCase> byProtocolsId(List<Long> protocolsId) {
        return protocolRepository.findAdmCasesByProtocolId(protocolsId);
    }
}
