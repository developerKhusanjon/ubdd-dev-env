package uz.ciasev.ubdd_service.entity.history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum TransDictAdminHistoricAction {

    CREATE(1, true),
    UPDATE(2, true),
    DELETE(3, false);

    private final int id;
    private final boolean hideDetail;

    public static TransDictAdminHistoricAction getInstanceById(Integer id) {
        if (id == 1) {
            return CREATE;
        } else if (id == 2) {
            return UPDATE;
        } else if (id == 3) {
            return DELETE;
        }
        throw new NotImplementedException(String.format("Enum value in TransDictAdminHistoricAction for id %s not present", id));
    }
}
