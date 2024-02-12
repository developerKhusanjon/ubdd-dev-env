package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.settings.BrvCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.BrvUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.settings.Brv;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamNotPresent;
import uz.ciasev.ubdd_service.exception.settings.MultipleActiveBrvException;
import uz.ciasev.ubdd_service.exception.settings.NoActiveBrvException;
import uz.ciasev.ubdd_service.repository.settings.BrvRepository;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.history.HistoryService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrvServiceImpl implements BrvService {

    private final BrvRepository repository;
    private final HistoryService historyService;

    @Override
    public Page<Brv> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Brv getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(Brv.class, id));
    }

    @Override
    public Long findRecent() {
        return findByDate(LocalDate.now());
    }

    @Override
    public Long findByDate(LocalDate date) {
        return repository
                .findByDate(date)
                .orElseThrow(() -> new EntityByParamNotPresent(Brv.class, "date", date));
    }

    @Override
    @Transactional
    public Brv create(BrvCreateRequestDTO requestDTO) {
        Brv oldBrv = getCurrentBrv();
        LocalDate oldBrvFromDate = oldBrv.getFromDate();
        LocalDate newBrvFromDate = requestDTO.getFromDate();

        if (!newBrvFromDate.isAfter(oldBrvFromDate)) {
            throw new ValidationException(
                    ErrorCode.GIVEN_FROM_DATE_IS_BEFORE_EXISTING_FROM_DATE,
                    "Given fromDate in the request body overlaps with existing Brv");
        }

        oldBrv.close(requestDTO.getFromDate().minusDays(1));
        repository.save(oldBrv);

        Brv newBrv = new Brv(requestDTO.getAmount(), requestDTO.getFromDate());
        historyService.registerBrvUpdate(repository.save(newBrv));

        return newBrv;
    }

    @Override
    @Transactional
    public void update(BrvUpdateRequestDTO requestDTO) {
        Brv brv = getCurrentBrv();
        brv.update(requestDTO.getAmount());
        historyService.registerBrvUpdate(repository.save(brv));
    }

    private Brv getCurrentBrv() {
        List<Brv> activeBrvList = repository.findCurrent();
        if (activeBrvList.isEmpty()) {
            throw new NoActiveBrvException();
        }

        if (activeBrvList.size() != 1) {
            throw new MultipleActiveBrvException();
        }

        return activeBrvList.get(0);
    }
}
