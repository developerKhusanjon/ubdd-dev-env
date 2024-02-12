package uz.ciasev.ubdd_service.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseRepository;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;

import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.EntityNameAlias.CASE;

@Service
@RequiredArgsConstructor
public class AdmCaseOrganServiceImpl implements AdmCaseOrganService {

    private final MibExecutionCardRepository cardRepository;
    private final InvoiceRepository invoiceRepository;
    private final DecisionRepository decisionRepository;
    private final PunishmentRepository punishmentRepository;
    private final CompensationRepository compensationRepository;
    private final AdmCaseRepository admCaseRepository;

    @Override
    public Organ by(Invoice invoice) {
            Optional<Organ> organOpt;
            switch (invoice.getOwnerTypeAlias()) {
                case PENALTY: {
                    organOpt = invoiceRepository.findAdmCaseOrganByInvoiceInPenaltyPunishment(invoice);
                    break;
                }
                case COMPENSATION: {
                    organOpt = invoiceRepository.findAdmCaseOrganByInvoiceInCompensation(invoice);
                    break;
                }
                default:
                    throw new NotImplementedException("GET INVOICE ORGAN OWNER TYPE NOT IMPLEMENTED");
            }

        return getOrThrow(organOpt, invoice);
    }

    @Override
    public Organ by(MibCardMovement movement) {
        return by(movement.getCard());
    }

    @Override
    public Organ by(MibExecutionCard card) {
        Optional<Organ> organOpt;

        switch (card.getOwnerTypeAlias()) {
            case DECISION: {
                organOpt = cardRepository.findAdmCaseOrganByDecisionCard(card);
            }
                break;
            case COMPENSATION: {
                organOpt = cardRepository.findAdmCaseOrganByCompensationCard(card);
                break;
            }
            default:
                throw new LogicalException("Get mibCard organ not implement for " + card.getOwnerTypeAlias());
        }

        return getOrThrow(organOpt, card);

    }

    @Override
    public Organ by(Decision decision) {
        return getOrThrow(
                decisionRepository.findAdmCaseOrganByDecision(decision),
                decision
        );
    }

    @Override
    public Organ by(Punishment punishment) {
        return punishmentRepository.findAdmCaseOrganByPunishment(punishment);
    }

    @Override
    public Organ by(Compensation compensation) {
        return compensationRepository.findAdmCaseOrganByCompensation(compensation);
    }

    @Override
    public Organ byAdmCaseId(Long caseId) {
        return getOrThrow(admCaseRepository.findOrganByAdmCaseId(caseId), CASE, caseId);
    }

    private Organ getOrThrow(Optional<Organ> optionalOrgan, AdmEntity admEntity) {
        return getOrThrow(
                optionalOrgan,
                admEntity.getEntityNameAlias(),
                admEntity.getId()
        );
    }

    private Organ getOrThrow(Optional<Organ> optionalOrgan, EntityNameAlias alias, Long id) {
        return optionalOrgan
                .orElseThrow(() -> new LogicalException(String.format(
                        "No found organ for %s with id %s",
                        alias,
                        id
                )));
    }
}
