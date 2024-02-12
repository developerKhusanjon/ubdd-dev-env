package uz.ciasev.ubdd_service.utils.types;

import uz.ciasev.ubdd_service.exception.NotFoundException;

import java.util.Arrays;

public enum GlobalSearchType {
    VIOLATOR,
    PARTICIPANT,
    VICTIM;

    public static GlobalSearchType findByText(String type) {
        return Arrays.stream(values())
                .filter(searchType -> searchType.name().equals(type)).findFirst()
                .orElseThrow(() -> new NotFoundException("Search type enum not found"));
    }
}
