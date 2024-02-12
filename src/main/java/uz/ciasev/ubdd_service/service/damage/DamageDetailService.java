package uz.ciasev.ubdd_service.service.damage;

import uz.ciasev.ubdd_service.dto.internal.response.adm.DamageDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;

import java.util.List;

public interface DamageDetailService {

    DamageDetail findById(Long id);

    List<DamageDetail> findAllByDamageId(Long id);

    List<DamageDetailResponseDTO> findAllByDamageIdAndProtocolId(Long damageId, Long protocolId);

    DamageDetail save(DamageDetail damageDetail);

    void delete(DamageDetail damageDetail);

    List<DamageDetail> findAllByProtocolId(Long protocolId);

    List<DamageDetail> findByProtocolIdAndVictimId(Long protocolId, Long victimId);
}
