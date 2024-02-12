package uz.ciasev.ubdd_service.service.damage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.DamageResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.damage.DamageRepository;
import uz.ciasev.ubdd_service.service.person.PersonService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DamageServiceImpl implements DamageService {

    private final DamageRepository damageRepository;
    private final PersonService personService;
    private final ViolatorService violatorService;
    private final VictimService victimService;

    @Override
    public Damage findById(Long id) {
        return damageRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Damage.class, id));
    }

    @Override
    public Damage getOrCreate(Violator violator, Victim victim, VictimType victimType) {
        return damageRepository
                .findByViolatorIdAndVictimTypeIdAndVictimId(violator.getId(), victimType.getId(), Optional.ofNullable(victim).map(Victim::getId).orElse(null))
                .orElseGet(() -> damageRepository.saveAndFlush(buildEmpty(violator, victim, victimType)));
    }

    @Override
    public void updateAmount(Damage damage, Long amount) {
        if (amount == 0) {
            damageRepository.delete(damage);
        } else {
            damage.setTotalAmount(amount);
            damageRepository.save(damage);
        }
        damageRepository.flush();
    }

    @Override
    public List<Damage> findByViolatorId(Long id) {
        return damageRepository.findByViolatorId(id);
    }

    @Override
    public List<Damage> findByVictimId(Long id) {
        return damageRepository.findByVictimId(id);
    }

    @Override
    public List<Damage> findByAdmCaseId(Long id) {
        return damageRepository.findByAdmCaseId(id);
    }

    @Override
    public List<Damage> findByViolatorAndAdmCaseIds(Long admCaseId, Long violatorId) {
        return damageRepository.findByViolatorAndAdmCaseIds(admCaseId, violatorId);
    }

    @Override
    public List<DamageResponseDTO> findAllByAdmCaseId(Long admCaseId) {
        return findByAdmCaseId(admCaseId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Damage buildEmpty(Violator violator, Victim victim, VictimType victimType) {
        return new Damage(violator, victim, victimType);
    }

    private DamageResponseDTO convertToDto(Damage damage) {
        if (Objects.isNull(damage)) {
            return null;
        }

        Violator violator = violatorService.getById(damage.getViolatorId());
        Person person = personService.findById(violator.getPersonId());

        VictimListResponseDTO victimListResponseDTO = null;
        if (Objects.nonNull(damage.getVictimId())) {
            Victim victim = victimService.findById(damage.getVictimId());
            victimListResponseDTO = new VictimListResponseDTO(
                    victim,
                    personService.findById(victim.getPersonId())
            );
        }

        return new DamageResponseDTO(damage, person, victimListResponseDTO, violator, null);
    }
}
