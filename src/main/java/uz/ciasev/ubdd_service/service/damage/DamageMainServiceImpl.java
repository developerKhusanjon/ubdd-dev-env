package uz.ciasev.ubdd_service.service.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.dict.DamageTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.damage.DamageDetailRepository;
import uz.ciasev.ubdd_service.service.AdmCaseChangeReasonService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.dict.DamageTypeDictionaryService;
import uz.ciasev.ubdd_service.service.dict.VictimTypeDictionaryService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;

@Service
@RequiredArgsConstructor
public class DamageMainServiceImpl implements DamageMainService {

    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final ProtocolService protocolService;
    private final VictimTypeDictionaryService victimTypeService;
    private final DamageTypeDictionaryService damageTypeService;
    private final VictimDetailService victimDetailService;
    private final DamageService damageService;
    private final DamageDetailService damageDetailService;
    private final AdmCaseChangeReasonService changeReasonService;
    private final DamageDetailRepository damageDetailRepository;

    @Override
    @Transactional
    public DamageDetail addDamageToProtocol(User user, DamageCreateRequestDTO requestDTO) {
        Protocol protocol = protocolService.findById(requestDTO.getProtocolId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.ADD_DAMAGE_TO_PROTOCOL, admCaseService.getByProtocolId(protocol.getId()));
        Violator violator = protocol.getViolatorDetail().getViolator();
        Victim victim = requestDTO.getVictimId() == null ? null :victimDetailService.findByVictimIdAndProtocolId(requestDTO.getVictimId(), protocol.getId()).getVictim();

        return addDamageToProtocol(user, protocol, violator, victim, requestDTO);
    }

    @Override
    @Transactional
    public DamageDetail addGovernmentDamageToProtocol(User user, Protocol protocol, Violator violator, Long amount) {
        DamageCreateRequestDTO requestDTO = new DamageCreateRequestDTO();
        requestDTO.setDamageType(damageTypeService.getByAlias(DamageTypeAlias.MATERIAL));
        requestDTO.setVictimType(victimTypeService.getByAlias(VictimTypeAlias.GOVERNMENT));
        requestDTO.setAmount(amount);

       return addDamageToProtocol(user, protocol, violator, null, requestDTO);
    }

    @Override
    @Transactional
    public DamageDetail addVictimDamageToProtocol(User user, Protocol protocol, Violator violator, Victim victim, DamageRequestDTO requestDTO) {
        DamageCreateRequestDTO createRequestDTO = new DamageCreateRequestDTO();
        createRequestDTO.setVictimType(victimTypeService.getByAlias(VictimTypeAlias.VICTIM));
        createRequestDTO.setDamageType(requestDTO.getDamageType());
        createRequestDTO.setAmount(requestDTO.getAmount());

        return addDamageToProtocol(user, protocol, violator, victim, createRequestDTO);
    }

    private DamageDetail addDamageToProtocol(User user, Protocol protocol, Violator violator, Victim victim, DamageCreateRequestDTO requestDTO) {
        Damage damage = damageService.getOrCreate(violator, victim, requestDTO.getVictimType());

        DamageDetail damageDetail = requestDTO.buildDamageDetail(user, damage, protocol);
        DamageDetail savedDamage = damageDetailService.save(damageDetail);

        recalculateDamageTotalAmount(damage);

        return savedDamage;
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.DAMAGE_EDIT)
    public DamageDetail updateDamageDetail(User user, Long id, DamageUpdateRequestDTO requestDTO) {
        DamageDetail damageDetail = damageDetailService.findById(id);
        Damage damage = damageService.findById(damageDetail.getDamageId());

        if (damage.getVictimType().not(VictimTypeAlias.VICTIM)
                && requestDTO.getDamageType().not(DamageTypeAlias.MATERIAL)) {
            throw new ValidationException(ErrorCode.DAMAGE_TYPE_MUST_BE_MATERIAL);
        }

        AdmCase admCase = admCaseService.getByProtocolId(damageDetail.getProtocolId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_DAMAGE, admCase);

        changeReasonService.create(user, admCase, damageDetail, requestDTO.getChangeReason());
        DamageDetail savedDamage = damageDetailService.save(requestDTO.applyTo(damageDetail));

        recalculateDamageTotalAmount(damage);

        return savedDamage;
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.DAMAGE_DELETE)
    public DamageDetail deleteDamageDetail(User user, Long id, ChangeReasonRequestDTO reason) {

        DamageDetail damageDetail = damageDetailService.findById(id);
        Damage damage = damageService.findById(damageDetail.getDamageId());
        AdmCase admCase = admCaseService.getByProtocolId(damageDetail.getProtocolId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.DELETE_DAMAGE_FROM_PROTOCOL, admCase);

        changeReasonService.create(user, admCase, damageDetail, reason);
        damageDetailService.delete(damageDetail);

        recalculateDamageTotalAmount(damage);

        return damageDetail;
    }

    @Override
    public void recalculateDamageTotalAmount(Damage damage) {
        long amount = damageDetailService
                .findAllByDamageId(damage.getId())
                .stream()
                .mapToLong(DamageDetail::getAmount)
                .sum();

        damageService.updateAmount(damage, amount);

    }

    @Override
    public void mergeTo(Damage damage, Violator toViolator, Victim toVictim) {
        if (damage.getVictimType().getAlias().equals(VictimTypeAlias.VICTIM) && toVictim == null) {
            throw new ValidationException(ErrorCode.DAMAGE_VICTIM_INVALID);
        }
        Damage newDamage = damageService.getOrCreate(
                toViolator,
                toVictim,
                damage.getVictimType()
        );
        damageDetailRepository.mergeAllTo(damage.getId(), newDamage.getId());
        recalculateDamageTotalAmount(newDamage);
    }
}
