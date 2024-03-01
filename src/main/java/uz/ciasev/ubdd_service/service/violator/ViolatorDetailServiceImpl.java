package uz.ciasev.ubdd_service.service.violator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorContactDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.violator.ViolatorDetailRepository;
import uz.ciasev.ubdd_service.repository.violator.ViolatorRepository;
import uz.ciasev.ubdd_service.service.LastEmploymentInfoService;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViolatorDetailServiceImpl implements ViolatorDetailService {

    private final AddressService addressService;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final ViolatorDetailRepository violatorDetailRepository;
    private final ViolatorRepository violatorRepository;
    private final LastEmploymentInfoService lastEmploymentInfoService;

    @Override
    public ViolatorDetail findById(Long id) {
        return violatorDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ViolatorDetail.class, id));
    }

    @Override
    public ViolatorDetail getMainByViolatorId(Long id) {
        return findMainByViolatorId(id)
                .orElseThrow(() -> new EntityByParamsNotFound(ViolatorDetail.class, "violatorId", id));
    }

    @Override
    public Optional<ViolatorDetail> findMainByViolatorId(Long id) {
        return violatorDetailRepository.findMainByViolatorId(id);
    }

    @Override
    public List<ViolatorDetail> findByViolatorId(Long id) {
        return violatorDetailRepository.findByViolatorId(id);
    }

    @Override
    public ViolatorDetail save(ViolatorDetail violatorDetail) {
        return violatorDetailRepository.save(violatorDetail);
    }

    @Transactional
    @Override
    public void setResidenceAddressForAll(Violator violator, Address address) {
        Address savedAddress = addressService.save(address);
        violatorDetailRepository.setResidenceAddressByViolator(violator.getId(), savedAddress.getId());
    }

    @Override
    public ViolatorDetail save(User user, ViolatorDetail violatorDetail) {
        return violatorDetailRepository.save(violatorDetail);
    }

    @Override
    public ViolatorDetailResponseDTO findDTOById(Long id) {
        return convertToDTO(findById(id));
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    @Transactional
    public ViolatorDetail updateViolatorDetail(User user, Long id, Long protocolId, ViolatorDetailRequestDTO requestDTO) {
        ViolatorDetail violatorDetail = findByViolatorIdAndProtocolId(id, protocolId);
        AdmCase admCase = admCaseService.getByViolator(violatorDetail.getViolator());

        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VIOLATOR_DETAIL, admCase);

        lastEmploymentInfoService.addLastEmploymentInfoToOwner(
                user, violatorDetail, requestDTO.getLastEmploymentInfo());

        return violatorDetailRepository.save(requestDTO.applyTo(violatorDetail));
    }

    @Override
    public ViolatorDetailResponseDTO convertToDTO(ViolatorDetail violatorDetail) {
        Violator violator = violatorDetail.getViolator();
        LastEmploymentInfoDTO lastEmploymentInfoDTO = lastEmploymentInfoService.getDTOByOwner(violatorDetail);

        return new ViolatorDetailResponseDTO(
                violatorDetail,
                lastEmploymentInfoDTO,
                violator,
                violator.getPerson(),
                Optional.ofNullable(violatorDetail.getDocumentGivenAddressId()).map(addressService::findDTOById).orElse(null)
        );
    }

    public ViolatorDetail findByViolatorIdAndProtocolId(Long violatorId, Long protocolId) {
        return violatorDetailRepository.findByViolatorIdAndProtocolId(violatorId, protocolId)
                .orElseThrow(() -> new EntityByParamsNotFound(ViolatorDetail.class, "violatorId", violatorId, "protocolId", protocolId));
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    @Transactional
    // Без этой транзакции удаление сущности минтруда будет происходить раньше апдейта сущности нарушителя
    public ViolatorDetail updateViolatorContactData(User user, Long violatorId, Long protocolId, ViolatorContactDataRequestDTO requestDTO) {

        ViolatorDetail violatorDetail = findByViolatorIdAndProtocolId(violatorId, protocolId);
        Violator violator = violatorDetail.getViolator();

        AdmCase admCase = admCaseService.getByViolator(violator);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_VIOLATOR_DETAIL, admCase);

        lastEmploymentInfoService.addLastEmploymentInfoToOwner(
                user, violatorDetail, requestDTO.getLastEmploymentInfo());

        violatorRepository.save(requestDTO.applyTo(violator));
        violatorDetailRepository.save(requestDTO.applyTo(violatorDetail));

        return violatorDetail;
    }
}
