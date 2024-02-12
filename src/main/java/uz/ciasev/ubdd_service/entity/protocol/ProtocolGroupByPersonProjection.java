package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.entity.Person;

public interface ProtocolGroupByPersonProjection {

    Person getPerson();
    String getActualAddressText();
    Long getProtocolId();
}
