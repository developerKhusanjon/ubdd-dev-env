package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MibResult {
    CORE_ERROR(-2, "Ошибка при обработке"),
    REQUEST_ID_NO_FOUND(-3, "Пакет документов не найден"),
    DOCUMENT_ID_AMBIGUOUS(201, "envelopeId и offenseId указаны вместе"),
    DOCUMENT_ID_EMPTY(201, "Обязательные данные отсутствуют"),
    FORBIDDEN(-4, "У вас нет доступа"),

    OK(0, "Успешно обработано"),
    ERROR(2, "Ошибка при обработке");

    private Integer code;
    private String message;


}
