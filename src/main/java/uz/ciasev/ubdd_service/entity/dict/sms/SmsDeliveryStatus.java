package uz.ciasev.ubdd_service.entity.dict.sms;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;

import javax.persistence.*;

@Entity
@Table(name = "d_sms_delivery_status")
@NoArgsConstructor
public class SmsDeliveryStatus extends AbstractEmiStatusDict {
}
