package uz.ciasev.ubdd_service.service.victim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimRequestCoreDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.victim.VictimDetailRepository;
import uz.ciasev.ubdd_service.repository.victim.VictimRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VictimServiceImpl implements VictimService {

    private final VictimRepository victimRepository;
    private final VictimDetailRepository victimDetailRepository;
    private final AddressService addressService;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;


    @Override
    public VictimResponseDTO findDTOById(Long id) {
        return convertToDTO(findById(id));
    }

    @Override
    public List<VictimResponseDTO> findAllDTOByAdmCaseId(Long caseId) {
        return findByAdmCaseId(caseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VictimResponseDTO> getFindVictims(Long admCaseId, Long violatorId, Long protocolId) {
        return findByAdmCaseOrViolatorOfProtocolViolator(admCaseId, violatorId, protocolId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Victim getOrSave(Victim victim) {

        Optional<Victim> victimOptional = victimRepository
                .findByAdmCaseIdAndPersonId(victim.getAdmCaseId(), victim.getPerson().getId());

        if (victimOptional.isPresent()) {
            return victimOptional.get();
        }

        Victim newVictim = victim.toBuilder()
                .actualAddress(addressService.copyOfId(victim.getActualAddressId()))
                .postAddress(addressService.copyOfId(victim.getPostAddressId()))
                .build();

//        victim.setId(null);
//        victim.setMergedToVictimId(null);
//
//        victim.setActualAddress();
//        victim.setPostAddress();

        return victimRepository.saveAndFlush(newVictim);
    }

    @Override
    public Victim findById(Long id) {
        return victimRepository.findById(id).orElseThrow(() -> new EntityByIdNotFound(Victim.class, id));
    }

    @Override
    public Long findByAdmCaseIdAndPersonId(Long admCaseId, Long personId) {
        return victimRepository
                .findIdByAdmCaseIdAndPersonId(admCaseId, personId)
                .orElseThrow(() -> new EntityByParamsNotFound(Victim.class, "personId", personId, "admCaseId", admCaseId));
    }

    @Override
    public List<String> findVictimsPinppByProtocolId(Long protocolId) {
        return victimRepository.findVictimsPinppByProtocolId(protocolId);
    }

    @Override
    public void delete(Victim victim) {
        victimRepository.delete(victim);
    }

    @Override
    public List<Victim> findByViolatorId(Violator violator) {
        return findByAdmCaseOrViolatorOfProtocolViolator(null, violator.getId(), null);
    }

    @Override
    public List<Victim> findByAdmCaseOrViolatorOfProtocolViolator(Long admCaseId, Long violatorId, Long protocolId) {
        return victimRepository.findByAdmCaseOrViolatorOrProtocol(admCaseId, violatorId, protocolId);
    }

    @Override
    @Transactional
    public Victim mergeTo(Victim oldVictim, AdmCase toAdmCase) {
        Victim newVictim = getOrSave(oldVictim.toBuilder().admCase(toAdmCase).build());
        victimDetailRepository.mergeAllTo(oldVictim.getId(), newVictim.getId());
        victimRepository.mergedTo(oldVictim.getId(), newVictim.getId());
        return newVictim;
    }

    @Override
    public List<Victim> findByAdmCaseId(Long admCaseId) {
        return victimRepository.findByAdmCaseId(admCaseId);
    }


    @Override
    public void updateVictimPostAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        Victim victim = findById(id);
        AdmCase admCase = admCaseService.getById(victim.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VICTIM_DETAIL, admCase);

        addressService.update(victim.getPostAddressId(), requestDTO);
    }

    @Override
    public void updateVictimActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        Victim victim = findById(id);
        AdmCase admCase = admCaseService.getById(victim.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VICTIM_DETAIL, admCase);

        addressService.update(victim.getActualAddressId(), requestDTO);
    }

    @Override
    public void updateVictim(User user, Long id, VictimRequestCoreDTO requestDTO) {
        Victim victim = findById(id);
        AdmCase admCase = admCaseService.getById(victim.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VICTIM_DETAIL, admCase);

        victimRepository.save(requestDTO.applyTo(victim));
    }

    @Override
    public VictimResponseDTO convertToDTO(Victim victim) {
        if (victim == null) {
            return null;
        }

        return new VictimResponseDTO(
                victim,
                victim.getPerson(),
                addressService.findDTOById(victim.getActualAddressId()),
                addressService.findDTOById(victim.getPostAddressId())
        );
    }

    @Override
    public VictimListResponseDTO convertToListDTO(Victim victim) {
        if (victim == null) {
            return null;
        }

        return new VictimListResponseDTO(
                victim,
                victim.getPerson()
        );
    }

    @Override
    public VictimListResponseDTO findListDTOById(Long id) {
        return convertToListDTO(findById(id));
    }
}