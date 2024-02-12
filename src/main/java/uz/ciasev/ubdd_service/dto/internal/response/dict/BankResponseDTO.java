package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Bank;

@Getter
public class BankResponseDTO extends DictResponseDTO {

    private final String mfo;

    public BankResponseDTO(Bank bank) {
        super(bank);
        this.mfo = bank.getMfo();
    }
}
