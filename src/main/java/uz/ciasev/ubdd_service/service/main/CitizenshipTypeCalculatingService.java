package uz.ciasev.ubdd_service.service.main;


import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;

public interface CitizenshipTypeCalculatingService {
    CitizenshipType calculate(PersonDocument document);
}
