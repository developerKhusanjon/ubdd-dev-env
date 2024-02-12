package uz.ciasev.ubdd_service.config.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDataObject<T> {

    private T object;
}
