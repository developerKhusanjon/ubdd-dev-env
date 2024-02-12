package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.CourtMibCardMovementSubscribeRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.ReturnRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibSverkaResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.service.dict.mib.MibCaseStatusService;
import uz.ciasev.ubdd_service.service.dict.mib.MibSendStatusService;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
@Profile({"local", "test"})
public class MibApiServiceMock implements MibApiService {

    private final MibSendStatusService mibSendStatusService;
    private final MibCaseStatusService mibCaseStatusService;

    private final Random rnd = new Random();

    @Override
    public MibResult sendExecutionCard(Long cardId, MibSendDecisionRequestApiDTO requestDTO) {
        return doSend();
    }

    @Override
    public MibResult sendSubscribeOnCourtEnvelope(Long cardId, CourtMibCardMovementSubscribeRequestApiDTO requestDTO) {
        return doSend();
    }

    @Override
    public MibSverkaResponseDTO getMibCase(Long cardId, String serial, String number) {
//        throw new MibApiException(new Exception("MOCK EXCEPTION MESSAGE"));
        MibSverkaResponseDTO response = new MibSverkaResponseDTO();
        response.setResultCode("0");
        response.setResultMsg("MOCK OK MESSAGE");
        response.setEnvelopeId((long) rnd.nextInt());
        response.setOffenseId(null);
        response.setExecutionCaseNumber("");
        response.setExecutionCaseStatus(mibCaseStatusService.findByCode("10"));
        response.setPayments(null);
        response.setDocument(null);

        return response;
    }

    @Override
    public MibResult sendExecutionCardReturnRequest(Long cardId, ReturnRequestApiDTO requestDTO) {
        return doSend();
    }

    private MibResult doSend() {
        switch ((int) (rnd.nextLong() % 3)) {
            case 0:
                return MibResult.builder()
                        .status(mibSendStatusService.getById(1L))
                        .requestId(rnd.nextLong())
                        .message("MOCK OK MESSAGE")
                        .build();
            case 1:
                return MibResult.builder()
                        .status(mibSendStatusService.getById(2L))
                        .requestId(rnd.nextLong())
                        .message("MOCK VALIDATION MESSAGE")
                        .build();
            default:
                throw new MibApiException(new Exception("MOCK EXCEPTION MESSAGE"));
        }
    }
}
