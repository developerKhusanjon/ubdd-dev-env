package uz.ciasev.ubdd_service.service.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.DamageDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.exception.FiltersNotSetException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.damage.DamageDetailRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DamageDetailServiceImpl implements DamageDetailService {

    private final DamageDetailRepository damageDetailRepository;
    private final DamageService damageService;
    private final ProtocolService protocolService;

    @Override
    public DamageDetail findById(Long id) {
        return damageDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(DamageDetail.class, id));
    }

    @Override
    public List<DamageDetail> findAllByDamageId(Long id) {
        return damageDetailRepository.findAllByDamageId(id);
    }

    @Override
    public List<DamageDetailResponseDTO> findAllByDamageIdAndProtocolId(Long damageId, Long protocolId) {
        if (damageId == null && protocolId == null) {
            throw FiltersNotSetException.onOfRequired("damageId", "protocolId");
        }

        return damageDetailRepository.AllByDamageIdAndProtocolId(damageId, protocolId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DamageDetail save(DamageDetail damageDetail) {
        return damageDetailRepository.save(damageDetail);
    }

    @Override
    public void delete(DamageDetail damageDetail) {
        damageDetailRepository.delete(damageDetail);
    }

    @Override
    public List<DamageDetail> findAllByProtocolId(Long id) {
        return damageDetailRepository.findAllByProtocolId(id);
    }

    private DamageDetailResponseDTO convertToDto(DamageDetail damageDetail) {
        if (Objects.isNull(damageDetail)) {
            return null;
        }

        DamageDetailResponseDTO responseDTO = new DamageDetailResponseDTO();
        Protocol protocol = protocolService.findById(damageDetail.getProtocolId());
        Damage damage = damageService.findById(damageDetail.getDamageId());

        responseDTO.setId(damageDetail.getId());
        responseDTO.setCreatedTime(damageDetail.getCreatedTime());
        responseDTO.setEditedTime(damageDetail.getEditedTime());
        responseDTO.setUserId(damageDetail.getUserId());
        responseDTO.setProtocolId(protocol.getId());
        responseDTO.setProtocolSeries(protocol.getSeries());
        responseDTO.setProtocolNumber(protocol.getNumber());
        responseDTO.setVictimId(damage.getVictimId());
        responseDTO.setVictimTypeId(damage.getVictimTypeId());
        responseDTO.setDamageId(damageDetail.getDamageId());
        responseDTO.setDamageTypeId(damageDetail.getDamageTypeId());
        responseDTO.setAmount(damageDetail.getAmount());

        return responseDTO;
    }

    @Override
    public List<DamageDetail> findByProtocolIdAndVictimId(Long protocolId, Long victimId) {
        return damageDetailRepository.findByProtocolIdAndVictimId(protocolId, victimId);
    }
}
