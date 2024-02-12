package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.repository.dict.DepartmentRepository;
import uz.ciasev.ubdd_service.repository.dict.DistrictRepository;
import uz.ciasev.ubdd_service.repository.dict.OrganRepository;
import uz.ciasev.ubdd_service.repository.dict.RegionRepository;
import uz.ciasev.ubdd_service.repository.settings.OrganInfoRepository;
import uz.ciasev.ubdd_service.service.UnknownValueService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganInfoAutoCreateServiceImpl implements OrganInfoAutoCreateService {

    private final OrganInfoRepository repository;
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final DepartmentRepository departmentRepository;
    private final OrganRepository organRepository;
    private final UnknownValueService unknownValueService;

    @Override
    @Transactional(timeout = 120)
    public Organ createForNew(Organ organ) {
        List<OrganInfo> organInfoList = new ArrayList<>();

        addOrganInfoForAllDistricts(organ, organInfoList);
        addEmptyOrganInfoForAllRegions(organ, organInfoList);
        addEmptyOrganInfoForOrgan(organ, organInfoList);

        repository.saveAll(organInfoList);
        return organ;
    }

    @Override
    @Transactional(timeout = 120)
    public Region createForNew(Region region) {
        List<OrganInfo> organInfoList = new ArrayList<>();

        addOrganInfoForAllDepartments(region, organInfoList);
        addEmptyOrganInfoForAllOrgans(region, organInfoList);

        repository.saveAll(organInfoList);
        return region;
    }

    @Override
    @Transactional(timeout = 120)
    public District createForNew(District district) {
        List<OrganInfo> organInfoList = new ArrayList<>();

        addOrganInfoForAllDepartments(district, organInfoList);
        addEmptyOrganInfoForAllOrgans(district, organInfoList);

        repository.saveAll(organInfoList);
        return district;
    }

    @Override
    @Transactional(timeout = 120)
    public Department createForNew(Department department) {
        List<OrganInfo> organInfoList = new ArrayList<>();

        addOrganInfoForAllDistricts(department, organInfoList);
        addEmptyOrganInfoForAllRegions(department, organInfoList);
        addEmptyOrganInfoForDepartment(department, organInfoList);

        repository.saveAll(organInfoList);
        return department;
    }

    private void addOrganInfoForAllDistricts(Organ organ, List<OrganInfo> organInfoList) {
        districtRepository.findAll()
                .stream()
                .map(district -> organInfoList.add(new OrganInfo(organ, district, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addOrganInfoForAllDistricts(Department department, List<OrganInfo> organInfoList) {
        districtRepository.findAll()
                .stream()
                .map(district -> organInfoList.add(new OrganInfo(department, district, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addOrganInfoForAllDepartments(Region region, List<OrganInfo> organInfoList) {
        departmentRepository.findAll()
                .stream()
                .map(department -> organInfoList.add(new OrganInfo(department, region, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addOrganInfoForAllDepartments(District district, List<OrganInfo> organInfoList) {
        departmentRepository.findAll()
                .stream()
                .map(department -> organInfoList.add(new OrganInfo(department, district, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addEmptyOrganInfoForAllRegions(Organ organ, List<OrganInfo> organInfoList) {
        regionRepository.findAll().stream()
                .map(region -> organInfoList.add(new OrganInfo(organ, region, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addEmptyOrganInfoForAllRegions(Department department, List<OrganInfo> organInfoList) {
        regionRepository.findAll().stream()
                .map(region -> organInfoList.add(new OrganInfo(department, region, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addEmptyOrganInfoForAllOrgans(Region region, List<OrganInfo> organInfoList) {
        organRepository.findAll().stream()
                .map(organ -> organInfoList.add(new OrganInfo(organ, region, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addEmptyOrganInfoForAllOrgans(District district, List<OrganInfo> organInfoList) {
        organRepository.findAll().stream()
                .map(organ -> organInfoList.add(new OrganInfo(organ, district, unknownValueService.getOrganInfoDescriptiveFields())))
                .collect(Collectors.toList());
    }

    private void addEmptyOrganInfoForDepartment(Department department, List<OrganInfo> organInfoList) {
        organInfoList.add(new OrganInfo(department, unknownValueService.getOrganInfoDescriptiveFields()));
    }

    private void addEmptyOrganInfoForOrgan(Organ organ, List<OrganInfo> organInfoList) {
        organInfoList.add(new OrganInfo(organ, unknownValueService.getOrganInfoDescriptiveFields()));
    }
}
