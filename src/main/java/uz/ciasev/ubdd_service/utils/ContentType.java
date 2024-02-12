package uz.ciasev.ubdd_service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContentType {

    OCTET_STREAM("application/octet-stream"),
    TEXT("text/plain"),
    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    PDF("application/pdf");

    private final String memoType;

}
