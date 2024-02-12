package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDrivingLicenseData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddDrivingLicenseDataRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddDrivingLicenseDataServiceImpl implements UbddDrivingLicenseDataService {

    private final UbddDrivingLicenseDataRepository repository;
    private final AddressService addressService;

    @Override
    public UbddDrivingLicenseData findById(User user, Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(UbddDrivingLicenseData.class, id));
    }

    @Override
    public UbddDriverLicenseResponseDTO findDTOById(User user, Long id) {

        return Optional.of(findById(user, id)).map(UbddDriverLicenseResponseDTO::new).get();
    }

    @Override
    @Transactional
    public UbddDrivingLicenseData save(User user, UbddDriverLicenseDTO dto) {

        UbddDrivingLicenseData drivingLicense = new UbddDrivingLicenseData();

        return save(user, drivingLicense, dto);
    }

    @Override
    @Transactional
    public UbddDriverLicenseResponseDTO saveAndGetDTO(User user, UbddDriverLicenseDTO dto) {

        return Optional.of(save(user, dto)).map(UbddDriverLicenseResponseDTO::new).get();
    }

    @Override
    @Transactional
    public UbddDrivingLicenseData update(User user, Long id, UbddDriverLicenseDTO dto) {

        UbddDrivingLicenseData drivingLicense = findById(user, id);

        return save(user, drivingLicense, dto);
    }

    private UbddDrivingLicenseData save(User user, UbddDrivingLicenseData drivingLicense, UbddDriverLicenseDTO dto) {

        drivingLicense.apply(dto);

        drivingLicense.setUserId(user.getId());

        Optional.ofNullable(dto.getDrivingLicenseGivenAddress())
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    drivingLicense.setDrivingLicenseGivenAddress(
                            addressService.save(a)
                    );
                });

        return repository.save(drivingLicense);
    }
}
