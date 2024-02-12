package uz.ciasev.ubdd_service.entity.dict.mib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.mib.MibReturnRequestReasonCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_mib_return_request_reason")
@NoArgsConstructor
@JsonDeserialize(using = MibReturnRequestReasonCacheDeserializer.class)
public class MibReturnRequestReason extends AbstractEmiDict {

}
