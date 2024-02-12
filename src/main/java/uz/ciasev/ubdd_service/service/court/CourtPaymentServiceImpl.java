package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtApiService;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtSendResult;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment_;
import uz.ciasev.ubdd_service.entity.court.SupplierType;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.court.CourtExecutionPaymentRepository;
import uz.ciasev.ubdd_service.service.court.files.CourtFileSendingService;
import uz.ciasev.ubdd_service.service.court.methods.FourMethodToCourtDTOService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.specifications.CourtExecutionPaymentSpecification;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtPaymentServiceImpl implements CourtPaymentService {

    private final CourtExecutionPaymentRepository repository;
    private final CourtApiService courtApiService;
    private final FourMethodToCourtDTOService fourMethodToCourtDTOService;
    private final InvoiceService invoiceService;
    private final CourtFileSendingService courtFileSendingService;
    private final CourtExecutionPaymentSpecification specification;

    @Override
    public void acceptIfCourt(Invoice invoice, Payment payment) {
        Decision decision = invoiceService.getInvoiceDecision(invoice);
        if (decision.getResolution().getOrgan().isCourt()) {
            accept(decision, invoice, payment);
        }
    }

    @Override
    public void accept(Decision decision, Invoice invoice, Payment payment) {
        var violator = decision.getViolator();
        var resolution = decision.getResolution();
        var paymentId = (payment != null) ? payment.getId() : null;
        var defendantId = (decision.getDefendantId() != null) ? decision.getDefendantId() : violator.getDefendantId();

        if (defendantId == null) {
            // Это или ручной материал, или суд не прислал дефендент.
            // В любом случае такую запись невозможно ни сохранить в базу, ни отправить в суд.
            return;
        }

        var courtPaymentData = CourtExecutionPayment
                .builder()
                .caseId(resolution.getAdmCase().getId())
                .claimId(resolution.getClaimId())
                .defendantId(defendantId)
                .personId(violator.getPersonId())
                .violatorId(violator.getId())
                .paymentId(paymentId)
                .invoiceSerial(invoice.getInvoiceSerial())
                .invoiceUrl(courtFileSendingService.buildInvoicePath(invoice))
                .supplierType(SupplierType.getValueFor(invoice.getOwnerTypeAlias()))
                .build();

        repository.save(courtPaymentData);
    }

    @Override
    public void send(CourtExecutionPayment courtExecutionPayment) {
        CourtRequestDTO<FourthCourtPaymentDTO> requestDTO = fourMethodToCourtDTOService.wrap(courtExecutionPayment);

        CourtSendResult result = null;
        try {
            result = courtApiService.sendInvoiceWithPayment(requestDTO);
        } catch (ApplicationException e) {
            setError(courtExecutionPayment, e.getMessage());
            return;
        }
        setSendingFlag(courtExecutionPayment, result);
    }

    @Override
    public List<CourtExecutionPayment> findAllUnsent() {
//        return repository.findAllUnsent().stream().limit(100).collect(Collectors.toList());
        return repository.findAll(
                specification.withIsSent(false),
                PageUtils.top(500, CourtExecutionPayment_.editedTime)
        ).toList();
    }

    @Override
    public List<CourtExecutionPayment> findAllRejected() {
//        return repository.findAllUnsent().stream().limit(100).collect(Collectors.toList());
        return repository.findAll(
                SpecificationsCombiner.andAll(
                        specification.withIsSent(true),
                        specification.withIsAccept(false)
                ),
                PageUtils.top(500, CourtExecutionPayment_.editedTime)
        ).toList();
    }

    private void setSendingFlag(CourtExecutionPayment courtExecutionPayment, CourtSendResult result) {
        courtExecutionPayment.setIsSent(true);
        if (result.isSuccess()) {
            courtExecutionPayment.setIsAccept(true);
        } else {
            courtExecutionPayment.setIsAccept(false);
            courtExecutionPayment.setRejectionReason(result.getMessage());
        }
        repository.save(courtExecutionPayment);
    }

    private void setError(CourtExecutionPayment courtExecutionPayment, String resultDescription) {
        courtExecutionPayment.setHasError(true);
        courtExecutionPayment.setError(resultDescription);
        repository.saveAndFlush(courtExecutionPayment);
    }

    @Override
    public CourtExecutionPayment findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(CourtExecutionPayment.class, id));
    }
}
