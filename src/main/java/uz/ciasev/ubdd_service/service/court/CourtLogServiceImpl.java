package uz.ciasev.ubdd_service.service.court;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtSendResult;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtLog;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.repository.court.CourtLogRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtLogServiceImpl implements CourtLogService {

    private final ObjectMapper mapper;
    private final CourtLogRepository courtLogRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourtLog save(CourtBaseDTO requestDTO, CourtMethod method) {
        return save(requestDTO, method, requestDTO.getCaseId(), requestDTO.getClaimId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourtLog save(Long caseId, CourtMethod method, CourtSendResult obj) {
        Long climeId = Optional.ofNullable(obj).map(CourtSendResult::getResponse).map(CourtResponseDTO::getEnvelopeId).orElse(null);

        return save(obj, method, caseId, climeId);
    }

    @Override
    public boolean hasSearchRequest(Decision decision) {
        return false;
    }

    private CourtLog save(Object requestDTO, CourtMethod method, Long caseId, Long claimId) {
        CourtLog courtLog = new CourtLog();
        courtLog.setMethod(method.name());
        courtLog.setCaseId(caseId);
        courtLog.setClaimId(claimId);

        String json = generateJson(requestDTO);
        courtLog.setData(json);

        return courtLogRepository.save(courtLog);
    }

    private String generateJson(Object requestDTO) {
        try {
            return mapper.writeValueAsString(requestDTO);
        } catch (JsonProcessingException exception) {
            return "Can't convert to JSON";
        }
    }
}
