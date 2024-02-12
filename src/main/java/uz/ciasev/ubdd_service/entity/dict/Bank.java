package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.BankDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.BankCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_bank")
@NoArgsConstructor
@JsonDeserialize(using = BankCacheDeserializer.class)
public class Bank extends AbstractEmiDict {

    @Getter
    private String mfo;

    public void construct(BankDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(BankDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(BankDTOI request) {
        this.mfo = request.getMfo();
    }
}