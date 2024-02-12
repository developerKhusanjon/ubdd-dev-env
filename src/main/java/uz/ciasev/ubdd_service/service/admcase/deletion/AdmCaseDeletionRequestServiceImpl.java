package uz.ciasev.ubdd_service.service.admcase.deletion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeletionDeclineRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.admcase.deletion.AdmCaseDeletionRequestRepository;
import uz.ciasev.ubdd_service.specifications.AdmCaseDeletionRequestSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AdmCaseDeletionRequestServiceImpl implements AdmCaseDeletionRequestService {

    private final AdmCaseDeletionRequestRepository repository;
    private final FilterHelper<AdmCaseDeletionRequest> filterHelper;
    private final AdmCaseDeletionRequestSpecifications specifications;

    @Override
    public AdmCaseDeletionRequest getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AdmCaseDeletionRequest.class, id));
    }

    @Override
    public void checkForPreExistingRequest(Long admCaseId) {
        if (repository.existsByAdmCaseIdAndStatus(admCaseId, AdmCaseDeletionRequestStatusAlias.CREATED))
            throw new ValidationException(ErrorCode.PENDING_DELETION_REQUEST_ALREADY_EXISTS);
    }

    @Override
    public void makeApproved(User admin, AdmCaseDeletionRequest deletionRequest, AdmCaseDeletionRegistration deletionRegistration) {
        deletionRequest.setStatus(AdmCaseDeletionRequestStatusAlias.APPROVED);
        deletionRequest.setAdmin(admin);
        deletionRequest.setRegistration(deletionRegistration);
        repository.save(deletionRequest);
    }

    @Override
    public void makeApproveCanceled(User admin, AdmCaseDeletionRequest deletionRequest) {
        deletionRequest.setStatus(AdmCaseDeletionRequestStatusAlias.APPROVE_CANCELED);
        repository.save(deletionRequest);
    }

    @Override
    public void makeDeclined(User admin, AdmCaseDeletionRequest deletionRequest, AdmCaseDeletionDeclineRequestDTO requestDTO) {
        AdmCaseDeletionRequest declinedRequest = requestDTO.applyTo(deletionRequest);
        declinedRequest.setAdmin(admin);
        declinedRequest.setStatus(AdmCaseDeletionRequestStatusAlias.DECLINED);
        repository.save(declinedRequest);
    }

    @Override
    public Page<AdmCaseDeletionRequestProjection> findAllByFilters(User user, Map<String, String> filters, Pageable pageable) {
        Page<Long> ids = repository.findAllId(
                specifications.inUserVisibility(user).and(filterHelper.getParamsSpecification(filters)),
                pageable
        );

        return getPageOfProjections(ids, pageable);
    }

    @Override
    public Page<AdmCaseDeletionRequestProjection> findAllByAdmCaseId(Long admCaseId, Pageable pageable) {
        Page<Long> ids = repository.findAllId(
                specifications.withAdmCaseId(admCaseId),
                pageable
        );

        return getPageOfProjections(ids, pageable);
    }

    @Override
    public Page<AdmCaseDeletionRequestProjection> findAllByUserId(User user, Map<String, String> filters, Pageable pageable) {
        Page<Long> ids = repository.findAllId(
                specifications.withUserId(user.getId()).and(filterHelper.getParamsSpecification(filters)),
                pageable
        );

        return getPageOfProjections(ids, pageable);
    }

    private Page<AdmCaseDeletionRequestProjection> getPageOfProjections(Page<Long> ids, Pageable pageable) {
        List<AdmCaseDeletionRequestProjection> projectionList = repository.findAllProjection(ids.getContent(), pageable.getSort());

        return new PageImpl<>(projectionList, pageable, ids.getTotalElements());
    }
}
