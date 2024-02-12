package uz.ciasev.ubdd_service.entity.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum VictimTypeAlias implements BackendAlias {
    GOVERNMENT(2L),
    JURIDIC(3L),
    VICTIM(1L);

    public final static String GOVERNMENT_ID = "2";
    private final long id;

    public static VictimTypeAlias getInstanceById(Long id) {
        if (id == 1) {
            return VICTIM;
        } else if (id == 2) {
            return GOVERNMENT;
        } else if (id == 3) {
            return JURIDIC;
        }

        throw new NotImplementedException(String.format("Enum value in VictimTypeAlias for id %s not present", id));
    }
}
