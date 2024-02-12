package uz.ciasev.ubdd_service.service.main;

import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.F1Document;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;

public interface PersonDataService {

    String getPhotoByDocument(PersonDocument personDocument);

    // Создет новую персону в БД
    @Transactional
    Pair<Person, ? extends PersonDocument> provideByPinppOrManualDocument(ActorRequest actorRequest);

    // Создет новую персону в БД
    @Transactional
    Pair<Person, F1Document> provideByPinpp(String pinpp);

    String getPhotoByPinpp(String pinpp);

}
