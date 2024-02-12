package uz.ciasev.ubdd_service.service.court.methods;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtDefendantDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtMunisDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth.FourthCourtPaymentDTO;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.service.invoice.PaymentService;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FourMethodToCourtDTOServiceImpl implements FourMethodToCourtDTOService {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final PaymentService paymentService;

    @Override
    public CourtRequestDTO<FourthCourtPaymentDTO> wrap(CourtExecutionPayment courtExecutionPayment) {
        var defendant = buildDefendant(courtExecutionPayment);
        var requestDTO = buildPayment(courtExecutionPayment, defendant);
        return wrapInvoice(requestDTO);
    }

    private CourtRequestDTO<FourthCourtPaymentDTO> wrapInvoice(FourthCourtPaymentDTO invoice) {
        var courtRequestDTO = new CourtRequestDTO<FourthCourtPaymentDTO>();
        courtRequestDTO.setSendDocumentRequest(invoice);

        return courtRequestDTO;
    }

    private FourthCourtPaymentDTO buildPayment(CourtExecutionPayment courtExecutionPayment, FourthCourtDefendantDTO defendant) {
        return FourthCourtPaymentDTO.builder()
                .caseId(courtExecutionPayment.getCaseId())
                .claimId(courtExecutionPayment.getClaimId())
                .courtExecutionPaymentId(courtExecutionPayment.getId())
                .defendant(List.of(defendant))
                .build();
    }

    private FourthCourtDefendantDTO buildDefendant(CourtExecutionPayment courtExecutionPayment) {
        return FourthCourtDefendantDTO.builder()
                .defendantId(courtExecutionPayment.getDefendantId())
                .violatorId(courtExecutionPayment.getPersonId())
                .invoiceSerial(courtExecutionPayment.getInvoiceSerial())
                .invoiceUrl(courtExecutionPayment.getInvoiceUrl())
                .supplierType(courtExecutionPayment.getSupplierType())
                .munis(buildMunis(courtExecutionPayment.getPaymentId()))
                .build();
    }

    private List<FourthCourtMunisDTO> buildMunis(Long paymentId) {
        if (paymentId == null)
            return Collections.emptyList();

        Optional<Payment> paymentOpt = paymentService.findById(paymentId);

        if (paymentOpt.isEmpty())
            return Collections.emptyList();

        Payment payment = paymentOpt.get();


        FourthCourtMunisDTO munisDTO = FourthCourtMunisDTO.builder()
//                .paymentTime(payment.getPaymentTime().plusNanos(666666666).format(dtf))
                .paymentTime(payment.getPaymentTime().format(dtf))
                .amount(payment.getAmount())
                .paymentId(payment.getBid())
                .blankNumber(payment.getBlankNumber())
                .blankDate(payment.getBlankDate())
                .fromBankCode(payment.getFromBankCode())
                .fromBankAccount(payment.getFromBankAccount())
                .fromBankName(payment.getFromBankName())
                .fromInn(payment.getFromInn())
                .toBankCode(payment.getToBankCode())
                .toBankAccount(payment.getToBankAccount())
                .toBankName(payment.getToBankName())
                .toInn(payment.getToInn())
                .build();
        return List.of(munisDTO);
    }
}
