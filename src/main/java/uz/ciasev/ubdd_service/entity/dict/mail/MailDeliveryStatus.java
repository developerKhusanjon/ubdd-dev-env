package uz.ciasev.ubdd_service.entity.dict.mail;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;

import javax.persistence.*;

@Entity
@Table(name = "d_mail_delivery_status")
@NoArgsConstructor
public class MailDeliveryStatus extends AbstractExternalStatusDictEntity {
}
