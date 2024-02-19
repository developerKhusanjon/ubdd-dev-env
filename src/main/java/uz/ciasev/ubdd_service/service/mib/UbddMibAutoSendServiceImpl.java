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
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
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
public class UbddMibAutoSendServiceImpl implements UbddMibAutoSendService {

    private final MibCardService mibCardService;
    private final DecisionService decisionService;
    private final SendToMibService mibService;
    private final MibAutoSendLogRepository mibAutoSendLogRepository;
    private final AddressService addressService;
    private final DistrictDictionaryService districtService;
    private final RegionDictionaryService regionService;


    @Override
    public void send(Long decisionId, MibResult mibResult) {
        MibExecutionCard executionCard;
        try {
            executionCard = executionCardByDecision(decisionId);
        } catch (NotFoundException e) {
            log.info("Execution Card By Decision Id:{} Failed", decisionId);
            return;
        }
        MibAutoSendLog mibAutoSendLog = new MibAutoSendLog(executionCard);
        try {
            process(executionCard, mibResult);
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
                null,
                decision,
                MibCardRequestDTO.builder()
                        .region(region)
                        .district(district)
                        .build()
        );
    }

    private void process(MibExecutionCard executionCard, MibResult mibResult) {
        mibService.doSend(executionCard, null, mibResult);
    }
}
