package uz.ciasev.ubdd_service.exception.transfer;


import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.utils.StringUtils;

public class DictTransferNotPresent extends ApplicationException {

    protected DictTransferNotPresent(Class transClass, String values) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format("%s_TRANSFER_ERROR", StringUtils.camelToUpperSnake(transClass.getSimpleName())),
                String.format(
                        "Transfer data no present in %s for %s",
                        transClass.getSimpleName(),
                        values
                ));

    }

    public DictTransferNotPresent(Class transClass, String field, Long value) {
        this(transClass, String.format("%s = %s", field, value));

    }

    public DictTransferNotPresent(Class transClass, AbstractDict entity) {
        this(transClass, String.format("internalId", entity.getId()));

    }

    public DictTransferNotPresent(Class transClass, String field1, Long value1, String field2, Long value2) {
        this(transClass, String.format("%s = %s, %s = %s", field1, value1, field2, value2));

    }

    public DictTransferNotPresent(Class transClass, String field1, Long value1, String field2, Long value2, String field3, Long value3) {
        this(transClass, String.format("%s = %s, %s = %s, %s = %s", field1, value1, field2, value2, field3, value3));

    }
}
