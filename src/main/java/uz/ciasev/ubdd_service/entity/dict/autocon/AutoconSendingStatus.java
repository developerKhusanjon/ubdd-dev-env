package uz.ciasev.ubdd_service.entity.dict.autocon;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_autocon_sending_status")
@NoArgsConstructor
public class AutoconSendingStatus extends AbstractBackendStatusDict<AutoconSendingStatusAlias> {
}
