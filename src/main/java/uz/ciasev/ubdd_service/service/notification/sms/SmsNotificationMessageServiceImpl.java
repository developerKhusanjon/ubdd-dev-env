package uz.ciasev.ubdd_service.service.notification.sms;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.*;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SmsNotificationMessageServiceImpl implements SmsNotificationMessageService {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String fs(Long coins) {
        return Double.toString(MoneyFormatter.coinToCurrency(coins));
    }

    private String fsd(Long coins) {
        return MoneyFormatter.coinToString(coins);
    }

    private String fd(LocalDate date) {
        return date.format(dateFormat);
    }

    @Override
    public String buildDecisionMessage(NotificationSmsTextDecisionRequestDTO requestDTO) {

        String msg = "";

        msg = requestDTO.getFio() + ", " +
                fd(requestDTO.getBirthDate()) +
                ". Invoys " +
                requestDTO.getPenaltyInvoiceNumber() +
                ". To'lov " +
                fd(requestDTO.getPenaltyPaymentDate()) + "dan. JARIMA " +

                fsd(requestDTO.getPenaltyAmount()) + " so'm";

        if (!PenaltyPunishment.DiscountVersion.NO.equals(requestDTO.getPenaltyDiscount())) {
            msg = msg + ", " + fd(requestDTO.getPenaltyPaymentDateDiscount()) + "gacha " +
                    fsd(requestDTO.getPenaltyDiscountAmount()) + " so'm (" + requestDTO.getPenaltyDiscount().getFirstAmountPercent() + " %)";
        }

        if (requestDTO.getDamageInvoiceNumber() != null) {
            msg = msg +
                    ". ZARAR davlatga " +
                    requestDTO.getDamageInvoiceNumber() + ", " +
                    fsd(requestDTO.getDamageAmount()) + " so'm";
        }

        if (requestDTO.getVictimDamageAmount() != null) {
            msg = msg +
                    ". Jabrlanuvchilarga " +
                    fsd(requestDTO.getVictimDamageAmount()) + " so'm";
        }

        String resolutionPlace = ". Organ " + requestDTO.getOrganName();
        if (!requestDTO.getDistrictName().isBlank()) {
            resolutionPlace += " / " + requestDTO.getDistrictName();
        } else if (!requestDTO.getRegionName().isBlank()) {
            //resolutionPlace += " / " + requestDTO.getRegionName() + " VILOYAT";
            resolutionPlace += " / " + requestDTO.getRegionName();
        }
        msg = msg + resolutionPlace;

        return msg;
    }

    @Override
    public String buildCourtMessage(NotificationSmsTextCourtRequestDTO requestDTO) {
        return requestDTO.getFio() + ", " +
                fd(requestDTO.getBirthDate()) +
                ". Sizning ishingiz " +
                requestDTO.getCourtRegionName() +
                " " +
                requestDTO.getCourtDistrictName() +
                " sudda. Sud No " +
                requestDTO.getRegNumber();
    }

    @Override
    public String buildMibMessage(NotificationSmsTextMibRequestDTO requestDTO) {
        return requestDTO.getFio() + ", " +
                fd(requestDTO.getBirthDate()) +
                ". JARIMA to'lov muddati " +
                fd(requestDTO.getMastByExecutedBeforeDate()) +
                "gacha. To'lov uchun qoldiq " +
                fs(requestDTO.getPaymentAmount()) +
                " so'm. Sizning ishingiz " +
                fd(requestDTO.getSendDateToMib()) +
                "dan MIBga o'tadi";
    }

    @Override
    public String buildProtocolMessage(NotificationSmsTextProtocolRequestDTO requestDTO) {

        return String.format("%s, %s. Bayonnoma %s-%s %sda rasmiylashtirildi. To'lov uchun qo'shimcha SMS kuting. Organ %s%s. Modda %s",
                requestDTO.getFio(),
                fd(requestDTO.getBirthDate()),
                requestDTO.getProtocolSeries(),
                requestDTO.getProtocolNumber(),
                fd(requestDTO.getRegistrationDate()),
                requestDTO.getOrganName(),
                String.format(" / %s", requestDTO.getDistrictName()),
                requestDTO.getArticleName());
    }

    @Override
    public String buildDigitalSignaturePasswordMessage(NotificationSmsTextDigitalSignaturePasswordRequestDTO requestDTO) {
//        return String.format("You digital signature %s password: %s. Expires on %s",
//                requestDTO.getSerial(),
//                requestDTO.getPassword(),
//                fd(requestDTO.getExpiresOn().toLocalDate())
//        );
        return String.format(
                "EMI tizimidan foydalanishda, Sizning sms kodi: %s (hech kimga bermang)",
                requestDTO.getPassword()
        );
    }
}
