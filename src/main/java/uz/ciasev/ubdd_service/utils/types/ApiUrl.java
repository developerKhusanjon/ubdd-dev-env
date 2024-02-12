package uz.ciasev.ubdd_service.utils.types;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.utils.serializer.ApiUrlSerializer;

@Getter
@JsonSerialize(using = ApiUrlSerializer.class)
public class ApiUrl {

    public static String SENT_MAIL_WEB_URL = "/mails/";
    public static String DECISION_WEB_URL = "/pdf/decision/";
    public static String SMS_WEB_URL = "/pdf/sms/";
    public static String INVOICE_WEB_URL = "/pdf/invoice/";
    public static String REQUIREMENT_WEB_URL = "/pdf/requirement/";
    public static String PROTOCOL_WEB_URL = "/pdf/protocol/";
    public static String PERSON_CARD_WEB_URL = "/pdf/person-card/";

    private final String uri;

    protected ApiUrl(String uri) {
        this.uri = uri;
    }

    public static ApiUrl getSentMailContent(MailNotificationListProjection mail) {
        return getSentMailContent(mail.getId());
    }

    public static ApiUrl getSentMailContent(MailNotification mail) {
        return getSentMailContent(mail.getId());
    }

    public static ApiUrl getDecisionInstance(Decision decision) {

        if (decision.getIsSavedPdf() && decision.getResolution().getCourtDecisionUri() != null) {
            return new LocalFileUrl(decision.getResolution().getCourtDecisionUri());
        }

        return new ApiUrl(DECISION_WEB_URL + decision.getId());
    }

    public static ApiUrl getDecisionInstance(Long decisionId) {

        return new ApiUrl(DECISION_WEB_URL + decisionId);
    }

    public static ApiUrl getProtocolInstance(Protocol protocol) {
        return new ApiUrl(PROTOCOL_WEB_URL + protocol.getId());
    }

    public static ApiUrl getPersonCardInstance(Long id) {
        return new ApiUrl(PERSON_CARD_WEB_URL + id);
    }

    public static ApiUrl getInvoiceInstance(Invoice invoice) {
        return new ApiUrl(INVOICE_WEB_URL + invoice.getId());
    }

    public static ApiUrl getSmsPdf(SmsFullListProjection sms) {
        return getSmsPdf(sms.getId());
    }

    public static ApiUrl getSmsPdf(SmsNotification sms) {
        return getSmsPdf(sms.getId());
    }

    private static ApiUrl getSmsPdf(Long id) {
        return new ApiUrl(SMS_WEB_URL + id);
    }

    private static ApiUrl getSentMailContent(Long id) {
        return new ApiUrl(SENT_MAIL_WEB_URL + id + "-mail-content.pdf");
    }
}
