package uz.ciasev.ubdd_service.service.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.AddressReferenceBook;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.AddressReferenceBookRepository;


@Service
@RequiredArgsConstructor
public class AddressReferenceBookServiceImpl implements AddressReferenceBookService {
    private final AddressReferenceBookRepository repository;

    @Override
    public AddressReferenceBook getAddressReferenceBookByPlace(Place place) {
        return repository.findByAlias(place.name()).orElseThrow(() -> new EntityByAliasNotPresent(AddressReferenceBook.class, place.name()));
    }
}
