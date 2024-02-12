package uz.ciasev.ubdd_service.entity.dict.resolution;

import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_manual_payment_source_type")
public class ManualPaymentSourceType extends AbstractBackendDict<ManualPaymentSourceTypeAlias> {
}
