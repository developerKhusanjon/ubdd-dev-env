package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.settings.DistrictIdListType;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrganAccountBuilderImpl implements OrganAccountBuilder {
    private final DictionaryService<Region> regionDictionaryService;
    private final DistrictDictionaryService districtDictionaryService;
    private final static DistrictIdListType RELATED_DISTRICT_FOR_ALL_REGIONS = DistrictIdListType.ofAllWithHead();


    @Override
    public List<OrganAccountSettingsCreateRequest> build(OrganAccountSettingsCreateRequestDTO requestDTO) {
        List<OrganAccountSettingsCreateRequest> result = new LinkedList<>();

        Collection<ArticlePart> articleParts = extractArticlePart(requestDTO);
        Map<Region, Collection<District>> geography = extractGeography(requestDTO);

        geography.forEach((region, districts) -> {
            districts.forEach(district -> {
                articleParts.forEach(articlePart -> {
                    result.add(build(requestDTO, region, district, articlePart));
                });
            });
        });

        return result;
    }

    private Collection<ArticlePart> extractArticlePart(OrganAccountSettingsCreateRequestDTO requestDTO) {
        if (requestDTO.getRelatedArticlePart().getIsAll()) {
            ArrayList<ArticlePart> result = new ArrayList<>();
            result.add(null);
            return result;
        }

        return requestDTO.getRelatedArticlePart().getValue();
    }

    private Map<Region, Collection<District>> extractGeography(OrganAccountSettingsCreateRequestDTO requestDTO) {
        Map<Region, Collection<District>> result = new HashMap<>();

        if (requestDTO.getRelatedRegion().getIsAll()) {
            regionDictionaryService.findAll()
                    .forEach(region -> addRegionWithDistracts(result, region, RELATED_DISTRICT_FOR_ALL_REGIONS));
        } else {
            addRegionWithDistracts(result, requestDTO.getRelatedRegion().getValue(), requestDTO.getRelatedDistrict());
        }

        return result;
    }

    private void addRegionWithDistracts(Map<Region, Collection<District>> map, Region region, DistrictIdListType relatedDistrict) {
        Collection<District> districts = new ArrayList<>();

        if (relatedDistrict.getIsAll()) {
            districts.addAll(districtDictionaryService.findAllByRegion(region));
        }

        if (relatedDistrict.isValuePresent()) {
            districts.addAll(relatedDistrict.getValue());
        }

        if (relatedDistrict.getIsHead()) {
            districts.add(null);
        }

        map.put(region, districts);
    }

    private OrganAccountSettingsCreateRequest build(OrganAccountSettingsCreateRequestDTO requestDTO, Region region, District district, ArticlePart articlePart) {
        var result = new OrganAccountSettingsCreateRequest();
        result.setOrgan(requestDTO.getOrgan());
        result.setDepartment(requestDTO.getDepartment());
        result.setBankAccountType(requestDTO.getBankAccountType());
        result.setRegion(region);
        result.setDistrict(district);
        result.setArticlePart(articlePart);
        return result;
    }
}
