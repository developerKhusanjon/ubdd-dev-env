package uz.ciasev.ubdd_service.service.notification.system;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class SystemUserNotificationMessageServiceImpl implements SystemUserNotificationMessageService {

    private static final String ADM_CASE_SEND_TO_ORGAN_MESSAGE_TEMPLATE = "Yuborish: Ma'muriy ish {admCase.number}";
    private static final String ADM_CASE_RETURN_FROM_ORGAN_MESSAGE_TEMPLATE = "Qaytarish (organ): Ma'muriy ish {admCase.number}";

    private static final String ADM_CASE_NOT_REGISTERED_IN_COURT_MESSAGE_TEMPLATE = "Qaytarish (sud): Ma'muriy ish {admCase.number}";
    private static final String COURT_MADE_RESOLUTION_MESSAGE_TEMPLATE = "Qaror (sud): Ma'muriy ish {admCase.number}";
    private static final String COURT_RETURN_ADM_CASE_MESSAGE_TEMPLATE = "Qaytarish (sud): Ma'muriy ish {admCase.number}";

    private static final String REGISTERED_MATERIAL_ON_DECISION_MESSAGE_TEMPLATE = "Sud - 315 modda: Qaror {decision.series}{decision.number}";
    private static final String DECISION_RETURN_FROM_MIB_MESSAGE_TEMPLATE = "Qaytarish (MIB): Qaror {decision.series}{decision.number}";


    private final Map<NotificationTypeAlias, String> templateMap = Map.of(
            NotificationTypeAlias.SEND_ADM_CASE_TO_ORGAN, ADM_CASE_SEND_TO_ORGAN_MESSAGE_TEMPLATE,
            NotificationTypeAlias.RETURN_ADM_CASE_FROM_ORGAN, ADM_CASE_RETURN_FROM_ORGAN_MESSAGE_TEMPLATE,

            NotificationTypeAlias.NOT_REGISTERED_IN_COURT, ADM_CASE_NOT_REGISTERED_IN_COURT_MESSAGE_TEMPLATE,
            NotificationTypeAlias.COURT_RESOLUTION_CREATE, COURT_MADE_RESOLUTION_MESSAGE_TEMPLATE,
            NotificationTypeAlias.COURT_RETURN_ADM_CASE, COURT_RETURN_ADM_CASE_MESSAGE_TEMPLATE,
            NotificationTypeAlias.REGISTERED_MATERIAL_315_IN_COURT, REGISTERED_MATERIAL_ON_DECISION_MESSAGE_TEMPLATE,

            NotificationTypeAlias.MIB_RETURN_DECISION, DECISION_RETURN_FROM_MIB_MESSAGE_TEMPLATE
    );


    @Override
    public String buildText(NotificationTypeAlias type, AdmCase admCase) {
        String template = getTemplate(type);
        Map<String, String> context = buildParamMap(admCase);
        return render(template, context);
    }

    @Override
    public String buildText(NotificationTypeAlias type, Decision decision) {
        String template = getTemplate(type);
        Map<String, String> context = buildParamMap(decision);
        return render(template, context);
    }

    private Map<String, String> buildParamMapI(AdmCase admCase) {
        Map<String, String> paramMap = new HashMap<>();

        Arrays.stream(AdmCase.class.getMethods())
                .filter(method -> method.getName().startsWith("get"))
                .forEach(method -> {
                    try {

                        String key = method.getName().toLowerCase(Locale.ROOT).replace("get", "admCase.");
                        Object value = method.invoke(admCase);

                        paramMap.put(key, String.valueOf(value));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        return paramMap;
    }

    private Map<String, String> buildParamMap(AdmCase admCase) {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("admCase.number", admCase.getNumber());
        paramMap.put("admCase.series", admCase.getSeries());

        return paramMap;
    }

    private Map<String, String> buildParamMap(Decision decision) {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("decision.number", decision.getNumber());
        paramMap.put("decision.series", decision.getSeries());

        return paramMap;
    }

    private String render(String template, Map<String, String> context) {
        String messageText = template;

        for (String k : context.keySet()) {
            messageText = messageText.replace(
                    "{" + k + "}",
                    context.get(k)
            );
        }
        ;

        return messageText;
    }

    private String getTemplate(NotificationTypeAlias type) {
        String template = templateMap.get(type);
        if (template == null) {
            //todo писать в логи  ане кидать эксепшен
            throw new RuntimeException("System user notification template not found for event type " + type.name());
        }
        return template;
    }
}
