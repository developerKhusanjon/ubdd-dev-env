package uz.ciasev.ubdd_service.service.address;

import uz.ciasev.ubdd_service.entity.AddressReferenceBook;

public interface AddressReferenceBookService {

    AddressReferenceBook getAddressReferenceBookByPlace(Place place);


    enum Place {
        UNKNOWN,
        CUSTOMS,
//        VAI, // Военная Автомобильная Инспекция
    }
}
