package uz.ciasev.ubdd_service.service.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.DeportationTerminal;
import uz.ciasev.ubdd_service.repository.dict.resolution.DeportationTerminalRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class DeportationTerminalDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<DeportationTerminal> implements DeportationTerminalDictionaryService {

    private final String subPath = "deportation-terminals";

    private final Class<DeportationTerminal> entityClass = DeportationTerminal.class;
    private final DeportationTerminalRepository repository;
}
