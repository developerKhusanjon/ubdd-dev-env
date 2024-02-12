package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MtpCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MtpUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.MtpResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.repository.dict.MtpRepository;

@Service
@RequiredArgsConstructor
@Getter
public class MtpDictionaryServiceImpl extends AbstractDictionaryCRUDService<Mtp, MtpCreateRequestDTO, MtpUpdateRequestDTO>
        implements MtpDictionaryService {

    private final String subPath = "mtps";
    private final TypeReference<MtpCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<MtpUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final MtpRepository repository;
    private final Class<Mtp> entityClass = Mtp.class;

    @Override
    public MtpResponseDTO buildResponseDTO(Mtp mtp) {
        return new MtpResponseDTO(mtp);
    }

    @Override
    public MtpResponseDTO buildListResponseDTO(Mtp entity) {return new MtpResponseDTO(entity);}
}
