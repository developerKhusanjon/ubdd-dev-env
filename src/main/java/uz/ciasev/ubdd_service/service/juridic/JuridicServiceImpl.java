package uz.ciasev.ubdd_service.service.juridic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.JuridicDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.JuridicRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class JuridicServiceImpl implements JuridicService {

    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AddressService addressService;
    private final JuridicRepository juridicRepository;

    @Override
    public Juridic findById(Long id) {

        return juridicRepository
                .findDetailById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Juridic.class, id));
    }

    @Override
    public JuridicDetailResponseDTO findDetailById(Long id) {

        Juridic juridic = findById(id);
        return new JuridicDetailResponseDTO(
                juridic,
                Optional.ofNullable(juridic.getFactAddressId()).map(addressService::findDTOById).orElse(null),
                Optional.ofNullable(juridic.getJurAddressId()).map(addressService::findDTOById).orElse(null)
        );
    }

    @Override
    @Transactional
    public Juridic create(User user, JuridicCreateRequestDTO juridicRequestDTO) {

        Juridic juridic = juridicRequestDTO.buildJuridic();

        Optional.ofNullable(juridicRequestDTO)
                .map(JuridicCreateRequestDTO::getJurAddress)
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    addressService.save(a);
                    juridic.setJurAddress(a);
                });
        Optional.ofNullable(juridicRequestDTO)
                .map(JuridicCreateRequestDTO::getFactAddress)
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    addressService.save(a);
                    juridic.setFactAddress(a);
                });

        return juridicRepository.save(juridic);
    }

    @Override
    @Transactional
    public Juridic replace(User user, Long juridicId, JuridicCreateRequestDTO juridicRequestDTO) {

        // rise constaint violation
//        if (juridicId != null) {
//            juridicRepository.deleteById(juridicId);
//        }

        if (juridicRequestDTO != null) {
            return create(user, juridicRequestDTO);
        }

        return null;
    }

    @Override
    @Transactional
    public Juridic delete(User user, Long juridicId) {

        if (juridicId != null) {
            juridicRepository.deleteById(juridicId);
        }

        return null;
    }

    @Override
    public Juridic update(User user, Long id, JuridicRequestDTO requestDTO) {

        Juridic juridic = findById(id);

        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_JURIDIC, admCaseService.findByJuridic(juridic));

        updateAddress(juridic,
                requestDTO.getFactAddress(),
                juridic.getFactAddressId(),
                juridic::setFactAddress,
                false);

        updateAddress(juridic,
                requestDTO.getJurAddress(),
                juridic.getJurAddressId(),
                juridic::setJurAddress,
                false);

        return juridicRepository.save(requestDTO.applyTo(juridic));
    }

    @Override
    public void updateFactAddress(User user, Long id, AddressRequestDTO addressRequestDTO) {

        Juridic juridic = findById(id);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_JURIDIC, admCaseService.findByJuridic(juridic));
        updateAddress(juridic,
                addressRequestDTO,
                juridic.getFactAddressId(),
                juridic::setFactAddress,
                true);
    }

    @Override
    public void updateJurAddress(User user, Long id, AddressRequestDTO addressRequestDTO) {

        Juridic juridic = findById(id);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_JURIDIC, admCaseService.findByJuridic(juridic));
        updateAddress(juridic,
                addressRequestDTO,
                juridic.getJurAddressId(),
                juridic::setJurAddress,
                true);
    }

    private void updateAddress(Juridic juridic,
                               AddressRequestDTO addressRequestDTO,
                               Long addressId,
                               Consumer<Address> addressSetter,
                               boolean updateJuridic) {

        if (addressRequestDTO == null) {
            if (addressId != null) {
                addressSetter.accept(null);
                if (updateJuridic) {
                    juridicRepository.save(juridic);
                }
            }
            return;
        }
        if (addressId != null) {
            addressService.update(addressId, addressRequestDTO);
        } else {
            addressSetter.accept(addressService.save(addressRequestDTO.buildAddress()));
            if (updateJuridic) {
                juridicRepository.save(juridic);
            }
        }
    }
}