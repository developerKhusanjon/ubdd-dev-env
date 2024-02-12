package uz.ciasev.ubdd_service.service.victim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.victim.VictimDetailRepository;
import uz.ciasev.ubdd_service.service.LastEmploymentInfoService;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VictimDetailServiceImpl implements VictimDetailService {

    private final AdmCaseAccessService admCaseAccessService;
    private final AddressService addressService;
    private final AdmCaseService admCaseService;
    private final VictimDetailRepository victimDetailRepository;
    private final LastEmploymentInfoService lastEmploymentInfoService;

    @Override
    public List<VictimDetail> findAllByProtocolId(Long protocolId) {
        return victimDetailRepository.findAllByProtocolId(protocolId);
    }

    @Override
    public VictimDetail findByVictimIdAndProtocolId(Long victimId, Long protocolId) {
        return victimDetailRepository
                .findByVictimIdAndProtocolId(victimId, protocolId)
                .orElseThrow(() -> new EntityByParamsNotFound(VictimDetail.class, "victimId", victimId, "protocolId", protocolId));
    }

    @Override
    public List<VictimDetailResponseDTO> findAllDTOByProtocolId(Long protocolId) {
        return findAllByProtocolId(protocolId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VictimDetail findById(Long id) {
        return victimDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(VictimDetail.class, id));
    }

    @Override
    public VictimDetailResponseDTO findDTOById(Long id) {
        return convertToDTO(findById(id));
    }

    @Override
    public VictimDetail save(VictimDetail victimDetail) {
        return victimDetailRepository.save(victimDetail);
    }

    @Override
    public void delete(VictimDetail victimDetail) {
        victimDetailRepository.delete(victimDetail);
    }

    @Override
    @Transactional
    // Без этой транзакции удаление сущности минтруда будет происходить раньше апдейта сущности нарушителя
    public VictimDetail updateVictimDetail(User user, Long id, Long protocolId, VictimDetailRequestDTO requestDTO) {
        VictimDetail victimDetail = findByVictimIdAndProtocolId(id, protocolId);
        AdmCase admCase = admCaseService.getById(victimDetail.getVictim().getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VICTIM_DETAIL, admCase);

        lastEmploymentInfoService.addLastEmploymentInfoToOwner(user, victimDetail, requestDTO.getLastEmploymentInfoDTO());

        return victimDetailRepository.save(requestDTO.applyTo(victimDetail));
    }

    @Override
    public boolean existsByVictim(Victim victim) {
        return victimDetailRepository.existsByVictimId(victim.getId());
    }

    @Override
    public VictimDetailResponseDTO convertToDTO(VictimDetail victimDetail) {
        if (victimDetail == null) {
            return null;
        }
        LastEmploymentInfoDTO lastEmploymentInfoDTO = lastEmploymentInfoService.getDTOByOwner(victimDetail);
        Victim victim = victimDetail.getVictim();
        return new VictimDetailResponseDTO(
                victimDetail,
                lastEmploymentInfoDTO,
                victim,
                victim.getPerson(),
                addressService.findDTOById(victimDetail.getDocumentGivenAddressId()));
    }

    @Override
    public boolean existsByProtocolIdAndPersonId(Long protocolId, Long personId) {
        Optional<VictimDetail> rsl = victimDetailRepository.findByProtocolIdAndPersonId(protocolId, personId);
        return rsl.isPresent();
    }
}
