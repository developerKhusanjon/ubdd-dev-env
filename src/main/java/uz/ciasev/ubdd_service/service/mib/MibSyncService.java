package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibSyncSendingRequestDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.mib.MibSverkaSending;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionCardRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperation;
import uz.ciasev.ubdd_service.service.aop.single_thread_operation.SingleThreadOperationResource;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;
import uz.ciasev.ubdd_service.service.dict.RegionDictionaryService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MibSyncService {
    private final DecisionService decisionService;
    private final MibCardService mibCardService;
    private final AddressService addressService;
    private final DistrictDictionaryService districtService;
    private final RegionDictionaryService regionService;
    private final MibExecutionCardRepository cardRepository;
    private final MibOwnerTypeService ownerTypeService;
    private final MibCardNotificationService cardNotificationService;
    private final MibSverkaOrderService sverkaOrderService;
    private final InvoiceService invoiceService;


    @SingleThreadOperation(type = SingleThreadOperationTypeAlias.SYNC_DECISION_WITH_MIB)
    public MibSverkaSending syncDecision(User user, @SingleThreadOperationResource Long decisionId, MibSyncSendingRequestDTO requestDTO) {
        boolean checkByInvoice = requestDTO.getCheckByInvoice();
        boolean checkByCardNumber = requestDTO.getCheckByCardNumber();

        Decision decision = decisionService.getById(decisionId);
        MibExecutionCard mibCard = mibCardService.findByDecisionId(decisionId).orElseGet(() -> createNewCard(user, decision));

        String controlSerialAndNumber;
        if (checkByInvoice) {
            controlSerialAndNumber = invoiceService.getPenaltyInvoiceByDecision(decision).getInvoiceSerial();
        } else if (checkByCardNumber) {
            controlSerialAndNumber = Optional.ofNullable(mibCard.getOutNumber()).orElseThrow(() -> new ValidationException(ErrorCode.MIB_CARD_HAS_NO_OUT_NUMBER));
        } else {
            controlSerialAndNumber = null;
        }

        String controlSerial;
        String controlNumber;
        if (controlSerialAndNumber != null) {
            if (!controlSerialAndNumber.startsWith("KV")) throw new ValidationException(ErrorCode.INVOICE_SERIAL_NOT_IS_KV);
            controlSerial = controlSerialAndNumber.substring(0, 2);
            controlNumber = controlSerialAndNumber.substring(2);
        } else {
            controlSerial = decision.getSeries();
            controlNumber = decision.getNumber();
        }

        MibSverkaSending sverka = sverkaOrderService.createByManualRequest(mibCard, controlSerial, controlNumber);
        sverkaOrderService.handel(sverka);
        return sverka;
    }


    private MibExecutionCard createNewCard(User user, Decision decision) {
        Address actualAddress = addressService.findById(decision.getViolator().getActualAddressId());
        Region region = actualAddress.getRegionIdOpt().map(regionService::getById).orElse(null);
        District district = actualAddress.getDistrictIdOpt().map(districtService::getById).orElse(null);


        MibExecutionCard card = new MibExecutionCard();
        card.setRegion(region);
        card.setDistrict(district);
        card.setDecision(decision);
        card.setOwnerTypeAlias(MibOwnerTypeAlias.DECISION);
        card.setUser(user);
        card.setOutNumber("USVERCA-MIB-" + decision.getNumber());
        card.setOutDate(LocalDate.now());
        cardNotificationService.setAutoNotification(card);
        return cardRepository.save(card);
    }

    public Page<MibSverkaSending> findByDecisionId(Long decisionId, Pageable pageable) {
        return sverkaOrderService.findByDecisionId(decisionId, pageable);
    }
}
