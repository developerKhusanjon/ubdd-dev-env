package uz.ciasev.ubdd_service.entity.history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum OrganAccountSettingsHistoricAction {

    CREATE(1, false),
    UPDATE(2, false),
    DELETE(3, true);

    private final int id;
    private final boolean hideDetail;

    public static OrganAccountSettingsHistoricAction getInstanceById(Integer id) {
        if (id == 1) {
            return CREATE;
        } else if (id == 2) {
            return UPDATE;
        } else if (id == 3) {
            return DELETE;
        }
        throw new NotImplementedException(String.format("Enum value in OrganAccountSettingsHistoricAction for id %s not present", id));
    }
}
