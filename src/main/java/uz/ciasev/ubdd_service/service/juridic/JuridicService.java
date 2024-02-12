package uz.ciasev.ubdd_service.service.juridic;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.JuridicDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.user.User;

public interface JuridicService {

    Juridic findById(Long id);

    JuridicDetailResponseDTO findDetailById(Long id);

    Juridic create(User user, JuridicCreateRequestDTO juridicRequestDTO);

    Juridic replace(User user, Long juridicId, JuridicCreateRequestDTO juridicRequestDTO);

    @Transactional
    Juridic delete(User user, Long juridicId);

    Juridic update(User user, Long id, JuridicRequestDTO requestDTO);

    void updateFactAddress(User user, Long id, AddressRequestDTO addressRequestDTO);

    void updateJurAddress(User user, Long id, AddressRequestDTO addressRequestDTO);
}
