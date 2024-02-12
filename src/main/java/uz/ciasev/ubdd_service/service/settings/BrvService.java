package uz.ciasev.ubdd_service.service.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.settings.BrvCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.BrvUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.settings.Brv;

import java.time.LocalDate;

public interface BrvService {

    Page<Brv> findAll(Pageable pageable);

    Brv getById(Long id);

    Long findRecent();

    Long findByDate(LocalDate date);

    Brv create(BrvCreateRequestDTO requestDTO);

    void update(BrvUpdateRequestDTO requestDTO);
}
