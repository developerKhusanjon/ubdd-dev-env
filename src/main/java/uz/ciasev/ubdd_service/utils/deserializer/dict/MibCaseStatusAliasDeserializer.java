package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractAliasDeserializer;

public class MibCaseStatusAliasDeserializer extends AbstractAliasDeserializer<MibCaseStatusAlias> {

    @Autowired
    protected MibCaseStatusAliasDeserializer() {
        super(MibCaseStatusAlias.class);
    }
}