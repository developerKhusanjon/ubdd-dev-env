package uz.ciasev.ubdd_service.service.ubdd_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddAttorneyLetterData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddAttorneyLetterDataRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddAttorneyLetterDataServiceImpl implements UbddAttorneyLetterDataService {

    private final UbddAttorneyLetterDataRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public UbddAttorneyLetterData save(User user, UbddAttorneyLetterDTO dto) {

        UbddAttorneyLetterData newData = new UbddAttorneyLetterData(dto, objectMapper);

        newData.setUserId(user.getId());

        return repository.save(newData);
    }

    @Override
    public UbddAttorneyLetterResponseDTO saveAndGetDTO(User user, UbddAttorneyLetterDTO dto) {

        return Optional.of(save(user, dto)).map(UbddAttorneyLetterResponseDTO::new).get();
    }

    @Override
    public UbddAttorneyLetterData findById(User user, Long id) {

        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(UbddAttorneyLetterData.class, id));
    }

    @Override
    public UbddAttorneyLetterResponseDTO findDTOById(User user, Long id) {

        return Optional.of(findById(user, id)).map(UbddAttorneyLetterResponseDTO::new).get();
    }
}
