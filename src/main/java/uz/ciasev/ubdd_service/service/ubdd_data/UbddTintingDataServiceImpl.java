package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTintingDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTintingResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTintingData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddTintingDataRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddTintingDataServiceImpl implements UbddTintingDataService {

    private final UbddTintingDataRepository repository;

    @Override
    public UbddTintingData findById(User user, Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(UbddTintingData.class, id));
    }

    @Override
    public UbddTintingResponseDTO findDTOById(User user, Long id) {

        return Optional.of(findById(user, id)).map(UbddTintingResponseDTO::new).get();
    }

    @Override
    @Transactional
    public UbddTintingData save(User user, UbddTintingDTO dto) {

        UbddTintingData ubddTintingData = new UbddTintingData();

        return save(user, ubddTintingData, dto);
    }

    @Override
    @Transactional
    public UbddTintingResponseDTO saveAndGetDTO(User user, UbddTintingDTO dto) {

        return Optional.of(save(user, dto)).map(UbddTintingResponseDTO::new).get();
    }

    @Override
    public UbddTintingData update(User user, Long id, UbddTintingDTO dto) {

        UbddTintingData ubddTintingData = findById(user, id);

        return save(user, ubddTintingData, dto);
    }

    private UbddTintingData save(User user, UbddTintingData ubddTintingData, UbddTintingDTO dto) {

        ubddTintingData.apply(dto);

        ubddTintingData.setUserId(user.getId());

        return repository.save(ubddTintingData);
    }
}
