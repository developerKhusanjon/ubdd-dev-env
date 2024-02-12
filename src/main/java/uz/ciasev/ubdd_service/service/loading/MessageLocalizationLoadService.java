package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.system.MessageLocalization;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.repository.system.MessageLocalizationRepository;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageLocalizationLoadService {

    private final MessageLocalizationRepository messageLocalizationRepository;

    public void load() throws IllegalAccessException {
        Field[] declaredFields = ErrorCode.class.getDeclaredFields();
        List<String> codes = new ArrayList<>();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && field.getType().isAssignableFrom(String.class)) {
                codes.add((String) field.get(null));
            }
        }

        Set<String> existCodes = messageLocalizationRepository.findAll().stream().map(MessageLocalization::getCode).collect(Collectors.toSet());

        codes.stream()
                .filter(code -> !existCodes.contains(code))
                .map(code -> {
                    MessageLocalization m = new MessageLocalization();
                    m.setCode(code);
                    String text = code.toLowerCase(Locale.ROOT).replace("_", " ");
                    m.setText(new MultiLanguage(text, text, text));
                    return m;
                })
                .forEach(messageLocalizationRepository::save);
    }
}
