package uz.ciasev.ubdd_service.entity.history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum DictAdminHistoricAction {

    INIT(-1, true), // записи сгенеренные автоматом в момент запуска админки справочников
    CREATE(1, true),
    UPDATE(2, true),
    CLOSE(3, false),
    OPEN(4, false);

    private final int id;
    private final boolean heedDetail;

    public static DictAdminHistoricAction getInstanceById(Integer id) {
        if (id == 1) {
            return CREATE;
        } else if (id == 2) {
            return UPDATE;
        } else if (id == 3) {
            return CLOSE;
        } else if (id == 4) {
            return OPEN;
        }
        throw new NotImplementedException(String.format("Enum value in DictAdminHistoricAction for id %s not present", id));
    }
}
