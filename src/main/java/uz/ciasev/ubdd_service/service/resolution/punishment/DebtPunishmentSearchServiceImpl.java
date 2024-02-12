package uz.ciasev.ubdd_service.service.resolution.punishment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.DebtPunishmentSearchDTO;
import uz.ciasev.ubdd_service.repository.resolution.punishment.DebtPunishmentSearchRepository;
import uz.ciasev.ubdd_service.service.dict.resolution.PunishmentTypeDictionaryService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtPunishmentSearchServiceImpl implements DebtPunishmentSearchService {

    private final DebtPunishmentSearchRepository repository;
    private final PunishmentTypeDictionaryService punishmentTypeDictionaryService;

    private List<Long> punishmentTypes;

    @PostConstruct
    private void init() {
        punishmentTypes = List.of(
                punishmentTypeDictionaryService.getByAlias(PunishmentTypeAlias.PENALTY).getId(),
                punishmentTypeDictionaryService.getByAlias(PunishmentTypeAlias.LICENSE_REVOCATION).getId()
        );
    }

    @Override
    public Page<DebtPunishmentSearchDTO> findByPinpp(User user, String pinpp, Pageable pageable) {

        List<Long> decisionsId = repository.findNotExecutedDecisionIsByPinpp(pinpp);
        return findNotExecutedPunishmentsByDecisionsId(user, decisionsId, pageable);
    }

    @Override
    public Page<DebtPunishmentSearchDTO> findByCarNumber(User user, String carNumber, Pageable pageable) {
        List<Long> decisionsId = repository.findNotExecutedDecisionIsByVehicleNumber(carNumber);
        return findNotExecutedPunishmentsByDecisionsId(user, decisionsId, pageable);
    }

    @Override
    public Page<DebtPunishmentSearchDTO> findByCarNumberAndPinpp(User user, String pinpp, String carNumber, Pageable pageable) {
        List<Long> decisionsId = new ArrayList<>();

        if (pinpp != null) {
            decisionsId.addAll(repository.findNotExecutedDecisionIsByPinpp(pinpp));
        }
        if (carNumber != null) {
            decisionsId.addAll(repository.findNotExecutedDecisionIsByVehicleNumber(carNumber));
        }

        return findNotExecutedPunishmentsByDecisionsId(user, decisionsId, pageable);
    }

    private Page<DebtPunishmentSearchDTO> findNotExecutedPunishmentsByDecisionsId(User user, List<Long> decisionsId, Pageable pageable) {
        return repository.findDebtPunishmentsByDecisionsId(
                        decisionsId,
                        user.getOrgan().getId(),
                        punishmentTypes,
                        pageable
                )
                .map(DebtPunishmentSearchDTO::of);
    }
}
