package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.mib.MibCardRequestDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.mib.MibAutoSendLog;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.temporary.UbddDecison202311;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.NotFoundException;
import uz.ciasev.ubdd_service.repository.mib.MibAutoSendLogRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;
import uz.ciasev.ubdd_service.service.dict.RegionDictionaryService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.temporary.UbddDecison202311Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MibAutoSendServiceImpl implements MibAutoSendService {

    private final MibCardService mibCardService;
    private final DecisionService decisionService;
    private final SendToMibService mibService;
    private final MibAutoSendLogRepository mibAutoSendLogRepository;
    private final AddressService addressService;
    private final DistrictDictionaryService districtService;
    private final RegionDictionaryService regionService;
    private final UbddDecison202311Service ubbdDecison202311Service;

    private User auto = User.builder().isActive(true).organCode("AUTO").isGod(true).build();

    @Override
    public void sendList(List<Long> decisions) {
        decisions.forEach((decisionId) -> {
            try {
                send(decisionId);
            } catch (Exception e) {
                log.error("MibAutoSendService error for send decision {}: {}", decisionId, e.getMessage(), e);
            }
        });
    }

    @Override
    public long sendListAndCount(Map<Long, String> decisionsAndNumbers) {
        int count = 0;
        for (Long decisionId : decisionsAndNumbers.keySet()) {
            UbddDecison202311 ubddDecison202311 = new UbddDecison202311(decisionsAndNumbers.get(decisionId), true, null, LocalDateTime.now());
            try {
                sendManual(decisionId);
                count++;
            } catch (Exception e) {
                log.error("MibAutoSendService error for send decision {}: {}", decisionId, e.getMessage(), e);
                ubddDecison202311.setInfo(e.getMessage());
                ubddDecison202311.setStatus(false);
            } finally {
                ubbdDecison202311Service.update(ubddDecison202311);
            }
        }
        return count;
    }

    private void sendManual(Long decisionId) {
        MibExecutionCard executionCard = null;
        try {
            executionCard = executionCardByDecision(decisionId);
        } catch (NotFoundException e) {
            log.error("Execution Card By Decision Id:{} Failed", decisionId, e);
        }
        MibAutoSendLog mibAutoSendLog = new MibAutoSendLog(executionCard);
        try {
            mibService.doSendManual(executionCard, auto);
        } catch (Exception e) {
            if (e.getMessage() == null) {
                e.printStackTrace();
            }
            mibAutoSendLog.setError(String.format("ERROR: %s, %s, %s", e.getMessage(), e.getLocalizedMessage(), e.getClass().getCanonicalName()));
        }
        mibAutoSendLogRepository.save(mibAutoSendLog);
    }

    @Override
    public void send(Long decisionId) {
        MibExecutionCard executionCard;
        try {
            executionCard = executionCardByDecision(decisionId);
        } catch (NotFoundException e) {
            log.info("Execution Card By Decision Id:{} Failed", decisionId);
            return;
        }
        MibAutoSendLog mibAutoSendLog = new MibAutoSendLog(executionCard);
        try {
            process(decisionId, executionCard);
        } catch (Exception e) {
            if (e.getMessage() == null) {
                e.printStackTrace();
            }
            mibAutoSendLog.setError(String.format("ERROR: %s, %s, %s", e.getMessage(), e.getLocalizedMessage(), e.getClass().getCanonicalName()));
        }
        mibAutoSendLogRepository.save(mibAutoSendLog);
    }

    private MibExecutionCard executionCardByDecision(Long decisionId) {
        Optional<MibExecutionCard> executionCardOptional = mibCardService.findByDecisionId(decisionId);

        return executionCardOptional.orElseGet(() ->
                generateCardByDecision(decisionId)
        );
    }

    private MibExecutionCard generateCardByDecision(Long decisionId) {
        Decision decision = decisionService.getById(decisionId);
        Address actualAddress = addressService.findById(decision.getViolator().getActualAddressId());
        Region region = actualAddress.getRegionIdOpt().map(regionService::getById).orElseThrow(() -> new NotFoundException("Card generation failed: region of violator's actual address is null"));
        District district = actualAddress.getDistrictIdOpt().map(districtService::getById).orElseThrow(() -> new NotFoundException("Card generation failed: district of violator's actual address is null"));

        return mibCardService.openCardForDecision(
                auto,
                decision,
                MibCardRequestDTO.builder()
                        .region(region)
                        .district(district)
                        .build()
        );
    }

    private void process(Long decisionId, MibExecutionCard executionCard) {

        // SEND TO MIB
        mibService.doSend(executionCard, auto);
    }
}
