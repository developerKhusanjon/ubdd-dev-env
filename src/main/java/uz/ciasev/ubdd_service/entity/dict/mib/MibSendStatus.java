package uz.ciasev.ubdd_service.entity.dict.mib;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictCreateDTOI;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_mib_send_status")
@NoArgsConstructor
public class MibSendStatus extends AbstractEmiStatusDict {

    public final static long SUCCESSFULLY_ID = 1L;
    public final static String SUCCESSFULLY_CODE = "0";

    public boolean isSuccessfully() {
        return isSuccessfully(this.id);
    }

    public static boolean isSuccessfully(Long id) {
        return SUCCESSFULLY_ID == id;
    }
    public static boolean isSuccessfullyCode(String code) {
        return SUCCESSFULLY_CODE.equals(code);
    }

}
