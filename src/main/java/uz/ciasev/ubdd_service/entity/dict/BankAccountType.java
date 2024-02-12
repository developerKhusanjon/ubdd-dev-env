package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.BankAccountTypeDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "bank_account_type")
@NoArgsConstructor
@JsonDeserialize(using = BankAccountTypeDeserializer.class)
public class BankAccountType extends AbstractEmiDict {
}
