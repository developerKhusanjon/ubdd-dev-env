package uz.ciasev.ubdd_service.service.dict;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.GeographyResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.DistrictResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.RegionResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransferDictionaryRepository;
import uz.ciasev.ubdd_service.service.ConvertDTOService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeographyServiceImpl implements GeographyService {

    private final RegionDictionaryService regionDictService;
    private final DistrictDictionaryService districtDictService;
    private final CourtTransferDictionaryRepository courtTransferDictionaryRepository;
    private final ConvertDTOService convertDTOService;

    @Override
    public GeographyResponseDTO getMibPresenceDTO() {

        List<RegionResponseDTO> regions = regionDictService.findAllMibPresence();
        List<DistrictResponseDTO> districts = districtDictService.findAllMibPresence();

        return new GeographyResponseDTO(regions, districts);
    }

    @Override
    public GeographyResponseDTO getCourtPresenceDTO() {

//        List<RegionResponseDTO> regions = regionDictService.findAllCourtPresence();
//        List<DistrictResponseDTO> districts = districtDictService.findAllCourtPresence();

        List<RegionResponseDTO> regions = courtTransferDictionaryRepository.findAllCourtRegions().stream().map(convertDTOService::convertRegionToDTO).collect(Collectors.toList());
        List<DistrictResponseDTO> districts = courtTransferDictionaryRepository.findAllCourtDistrict().stream().map(DistrictResponseDTO::new).collect(Collectors.toList());

        return new GeographyResponseDTO(regions, districts);
    }

    @Override
    public Boolean isMibPresent(Region region) {
        Optional<Region> rsl = regionDictService.findByIdMibPresence(region.getId());
        return rsl.isPresent();
    }

    @Override
    public Boolean isMibPresent(District district) {
        Optional<District> rsl = districtDictService.findByIdMibPresence(district.getId());
        return rsl.isPresent();
    }

    @Override
    public Boolean isCourtPresent(Region region) {
        Optional<Region> rsl = regionDictService.findByIdCourtPresence(region.getId());
        return rsl.isPresent();
    }

    @Override
    public Boolean isCourtPresent(District district) {
        Optional<District> rsl = districtDictService.findByIdCourtPresence(district.getId());
        return rsl.isPresent();
    }
}
