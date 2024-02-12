package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.AdmEntityNumber;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.repository.AdmEntityNumberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

    private final AdmEntityNumberRepository admEntityNumberRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long incrementAndGet(EntityNameAlias entityNameAlias, long year) {

        Optional<AdmEntityNumber> admEntityNumberOptional = admEntityNumberRepository.findByAlias(entityNameAlias, year);

        AdmEntityNumber admEntityNumber = admEntityNumberOptional.orElse(
                AdmEntityNumber.builder().alias(entityNameAlias).number(0L).year(year).build()
        );

        Long numValue = admEntityNumber.getNumber() + 1;
        admEntityNumber.setNumber(numValue);
        admEntityNumberRepository.saveAndFlush(admEntityNumber);

        return numValue;
    }
}
