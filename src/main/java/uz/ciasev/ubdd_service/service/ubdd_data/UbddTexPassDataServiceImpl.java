package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddTexPassDataRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddTexPassDataServiceImpl implements UbddTexPassDataService {

    private final UbddTexPassDataRepository repository;
    private final AddressService addressService;
    private final VehicleNumberTypeCalculationService numberTypeCalculationService;

    @Override
    public UbddTexPassData getById(@Nullable User user, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(UbddTexPassData.class, id));
    }

    @Override
    public UbddTexPassResponseDTO getDTOById(User user, Long id) {
        return Optional.of(getById(user, id)).map(UbddTexPassResponseDTO::new).get();
    }

    @Override
    @Transactional
    public UbddTexPassData save(@Nullable User user, UbddTexPassDTOI dto) {
        UbddTexPassData ubddTexPassData = new UbddTexPassData();

        return save(user, ubddTexPassData, dto);
    }

    @Override
    public UbddTexPassResponseDTO saveAndGetDTO(User user, UbddTexPassDTO dto) {
        return Optional.of(save(user, dto)).map(UbddTexPassResponseDTO::new).get();
    }

    @Override
    public UbddTexPassData update(User user, Long id, UbddTexPassDTO dto) {
        UbddTexPassData ubddTexPassData = getById(user, id);

        return save(user, ubddTexPassData, dto);
    }

    private UbddTexPassData save(@Nullable User user, UbddTexPassData ubddTexPassData, UbddTexPassDTOI dto) {
        ubddTexPassData.apply(dto);

        ubddTexPassData.setUser(user);
        ubddTexPassData.setVehicleNumberType(numberTypeCalculationService.calculateForNumber(ubddTexPassData.getVehicleNumber()).orElse(null));

        Optional.ofNullable(dto.getTexPassGivenAddress())
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    ubddTexPassData.setTexPassGivenAddress(
                            addressService.save(a)
                    );
        });

        Optional.ofNullable(dto.getVehicleOwnerAddress())
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    ubddTexPassData.setVehicleOwnerAddress(
                            addressService.save(a)
                    );
                });

        Optional.ofNullable(dto.getVehicleOwnerBirthAddress())
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    ubddTexPassData.setVehicleOwnerBirthAddress(
                            addressService.save(a)
                    );
                });

        Optional.ofNullable(dto.getVehicleOwnerDocumentGivenAddress())
                .map(AddressRequestDTO::buildAddress)
                .ifPresent(a -> {
                    ubddTexPassData.setVehicleOwnerDocumentGivenAddress(
                            addressService.save(a)
                    );
                });

        return repository.save(ubddTexPassData);
    }
}
