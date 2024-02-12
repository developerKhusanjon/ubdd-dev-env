package uz.ciasev.ubdd_service.service.violator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.repository.violator.ViolatorRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViolatorDTOServiceImpl implements ViolatorDTOService {

    private final ViolatorRepository violatorRepository;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AddressService addressService;
    private final ProtocolService protocolService;
    private final ViolatorService violatorService;

    public Violator save(Violator violator) {
        return violatorRepository.saveAndFlush(violator);
    }


    @Override
    public List<ViolatorResponseDTO> findAllDTOByAdmCaseId(Long admCaseId) {
        return violatorRepository
                .findByAdmCaseId(admCaseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ViolatorResponseDTO findDTOById(Long id) {
        return convertToDTO(violatorService.getById(id));
    }

    private Violator checkAndDo(User user, ActionAlias action, Long violatorId, Consumer<Violator> consumer) {
        Violator violator = violatorService.getById(violatorId);
        AdmCase admCase = admCaseService.getById(violator.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, action, admCase);
        consumer.accept(violator);
        return violator;
    }

    @Override
    public ViolatorResponseDTO convertToDTO(Violator violator) {
        if (violator == null) {
            return null;
        }

        return new ViolatorResponseDTO(
                violator,
                violator.getPerson(),
                ConvertUtils.protocolArticleToDTO(protocolService.getViolatorArticles(violator)),
                addressService.findDTOById(violator.getActualAddressId()),
                addressService.findDTOById(violator.getPostAddressId()),
                addressService.findDTOById(violator.getPerson().getBirthAddressId())
        );
    }

    @Override
    public ViolatorListResponseDTO convertToListDTO(Violator victim) {
        if (victim == null) {
            return null;
        }

        return new ViolatorListResponseDTO(
                victim,
                victim.getPerson()
        );
    }
}