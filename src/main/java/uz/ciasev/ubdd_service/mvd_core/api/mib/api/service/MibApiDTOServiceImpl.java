package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request.*;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.transfer.MibDictTransferService;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.MibSendDecisionRequestApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop.SignatureDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.mib.MibCardDocumentService;
import uz.ciasev.ubdd_service.service.document.generated.DocumentAutoGenerationEventType;
import uz.ciasev.ubdd_service.service.document.generated.GenerateDocumentService;
import uz.ciasev.ubdd_service.service.mib.MibCardNotificationService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolUbddDataService;
import uz.ciasev.ubdd_service.service.user.UserService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;
import uz.ciasev.ubdd_service.utils.FormatUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MibApiDTOServiceImpl implements MibApiDTOService {

    private final ProtocolService protocolService;
    private final FileService fileService;
    private final AddressService addressService;
    private final MibDictTransferService mibDictTransferService;
    private final MibCardDocumentService documentService;
    private final UserService userService;
    private final GenerateDocumentService pdfService;
    private final AliasedDictionaryService<DocumentType, DocumentTypeAlias> documentTypeDictionaryService;
    private final ProtocolUbddDataService protocolUbddDataService;
    private final ArticlePartDictionaryService articlePartService;
    private final MibCardNotificationService notificationService;

    @Override
    public MibSendDecisionRequestApiDTO buildSendDecisionRequest(MibExecutionCard card) {

        Decision decision = card.getDecision();
        PenaltyPunishment penalty = decision.getMainPunishment().getPenalty();
        Resolution resolution = decision.getResolution();
        Protocol protocol = protocolService.findSingleMainByAdmCaseId(resolution.getAdmCaseId());
        Optional<ProtocolUbddTexPassData> texPass = protocolUbddDataService.findTexPassByProtocolId(protocol.getId());
        ViolatorDetail violatorDetail = protocol.getViolatorDetail();
        Violator violator = decision.getViolator();
        Person violatorPerson = violator.getPerson();
        Address documentGivenAddress = addressService.findById(violatorDetail.getDocumentGivenAddressId());
        Address actualAddress = addressService.findById(violator.getActualAddressId());
        ArticlePart articlePart = Optional.ofNullable(decision.getArticlePartId())
                .map(articlePartService::getById)
                .orElse(protocol.getArticlePart());
        DecisionNotification notification = notificationService.getPresetNotification(card).orElse(null);

        String considerFIO;
        String considerPosition;
        if (resolution.getOrgan().isCourt()) {
            considerFIO = resolution.getConsiderInfo();
            considerPosition = "HAKAM";
        } else {
            User consider = userService.findById(resolution.getUserId());
            considerFIO = consider.getFio();
            considerPosition = consider.getPosition().getDefaultName();
        }


        Address residenceAddress = Stream.of(
                        violatorDetail.getResidenceAddressId(),
                        violatorDetail.getF1AddressId(),
                        violator.getActualAddressId()
                ).filter(Objects::nonNull)
                .map(addressService::findById)
                .filter(a -> a.getRegionId() != null)
                .findFirst()
                .get();

        MibCardDecisionDTO requestDTO = new MibCardDecisionDTO();

        requestDTO.setMibRequestId(String.valueOf(card.getDecisionId()));
        requestDTO.setMibRegionId(mibDictTransferService.transRegion(card.getRegionId(), card.getDistrictId()));
        requestDTO.setProtocolType(mibDictTransferService.getProtocolType(protocol));
        requestDTO.setProtocolTime(resolution.getResolutionTime());

        requestDTO.setResolutionTime(resolution.getResolutionTime());
        requestDTO.setResolutionRegionId(mibDictTransferService.transRegion(resolution.getRegion(), resolution.getDistrict()));
        requestDTO.setResolutionOrganId(mibDictTransferService.transOrgan(resolution.getOrgan()));
        requestDTO.setConsiderName(considerFIO);
        requestDTO.setConsiderPosition(considerPosition);

        requestDTO.setDecisionSeries(decision.getSeries());
        requestDTO.setDecisionNumber(decision.getNumber());
        requestDTO.setDecisionArticlePartId(mibDictTransferService.transArticlePart(articlePart));

        requestDTO.setProtocolInspectorName(protocol.getInspectorInfo());
        requestDTO.setProtocolViolationDate(protocol.getViolationTime().toLocalDate());
        requestDTO.setProtocolFabula(protocol.getFabula());
        requestDTO.setPenaltyAmount(penalty.getAmount());

        requestDTO.setAutoGovNumber(protocol.getVehicleNumber());
        texPass.ifPresent(ubddData -> {
            requestDTO.setAutoTechnicalPassportSerial(ubddData.getTexPassSeries());
            requestDTO.setAutoTechnicalPassportNumber(ubddData.getTexPassNumber());
        });

        if (notification != null) {
            requestDTO.setNotificationSentDate(notification.getSendDate());
            requestDTO.setNotificationReceiveDate(notification.getReceiveDate());
            requestDTO.setNotificationNumber(notification.getNumber());
            requestDTO.setNotificationText(notification.getText());
        }

        ViolatorRequestDTO violatorDTO = new ViolatorRequestDTO();

        violatorDTO.setResidenceAddressRegionId(
                //mibDictTransferService.transRegion(residenceAddress.getRegion(), residenceAddress.getDistrict())
                mibDictTransferService.transRegion(actualAddress)
        );

        violatorDTO.setResidenceAddress(FormatUtils.addressToText(residenceAddress));
        violatorDTO.setActualAddress(FormatUtils.addressToText(actualAddress));
        violatorDTO.setLastName(violatorPerson.getLastNameLat());
        violatorDTO.setFirstName(violatorPerson.getFirstNameLat());
        violatorDTO.setSecondName(violatorPerson.getSecondNameLat());
        violatorDTO.setLastNameRu(violatorPerson.getLastNameKir());
        violatorDTO.setFirstNameRu(violatorPerson.getFirstNameKir());
        violatorDTO.setSecondNameRu(violatorPerson.getSecondNameKir());
        violatorDTO.setCitizenTypeId(mibDictTransferService.transCitizenType(violatorPerson.getCitizenshipType()));
        violatorDTO.setGenderId(mibDictTransferService.transGender(violatorPerson.getGender()));
        violatorDTO.setOccupationId(mibDictTransferService.transOccupationId(violatorDetail.getOccupationId()));
        violatorDTO.setEmploymentPosition(violatorDetail.getEmploymentPosition());
        violatorDTO.setEmploymentPlace(violatorDetail.getEmploymentPlace());
        if (violatorPerson.isRealPinpp()) {
            violatorDTO.setPinpp(violatorPerson.getPinpp());
        }
        violatorDTO.setPersonDocumentTypeId(mibDictTransferService.transPersonDocumentType(violatorDetail.getPersonDocumentType()));
        violatorDTO.setDocumentSeries(violatorDetail.getDocumentSeries());
        violatorDTO.setDocumentNumber(violatorDetail.getDocumentNumber());
        violatorDTO.setDocumentGivenAddress(FormatUtils.addressToText(documentGivenAddress));
        violatorDTO.setDocumentGivenDate(violatorDetail.getDocumentGivenDate());
        violatorDTO.setBirthdate(violatorPerson.getBirthDate());

        requestDTO.setViolator(violatorDTO);

        List<MibCardDocument> documents = documentService.findAllAttachableDocumentsByCard(card);
        List<CardDocumentDTO> documentDTOS = documents.stream()
                .map(this::buildCardDocumentDTO)
                .collect(Collectors.toList());

        documentDTOS.add(getDecisionPdfDTO(decision));
        getNotificationPdfDTOOpt(card).ifPresent(documentDTOS::add);

        requestDTO.setDocuments(documentDTOS);

        MibSendDecisionRequestApiDTO requestWrapper = new MibSendDecisionRequestApiDTO(requestDTO, new SignatureDTO());
        return requestWrapper;
    }

    @Override
    public CourtMibCardMovementSubscribeRequestApiDTO buildCourtSubscribeRequestDTO(MibCardMovement movement) {
        return new CourtMibCardMovementSubscribeRequestApiDTO(movement);
    }

    @Override
    public ReturnRequestApiDTO buildReturnRequestDTO(Decision decision, MibCardMovementReturnRequest returnRequest) {
        ReturnRequestApiDTO requestApiDTO = new ReturnRequestApiDTO();

        requestApiDTO.setMibRequestId(returnRequest.getMovement().getMibRequestId());
        requestApiDTO.setDecisionSeries(decision.getSeries());
        requestApiDTO.setDecisionNumber(decision.getNumber());
        requestApiDTO.setReasonId(returnRequest.getReason().getCode());
        requestApiDTO.setReasonName(returnRequest.getReason().getDefaultName());
        requestApiDTO.setComment(returnRequest.getComment());
        requestApiDTO.setInspectorInfo(returnRequest.getUser().getInfo());


        byte[] fileContent = pdfService.getMibReturnRequest(returnRequest, DocumentAutoGenerationEventType.SEND_TO_MIB);
        ReturnRequestFileApiDTO fileApiDTO = new ReturnRequestFileApiDTO();
        fileApiDTO.setMd5Hash(DigestUtils.md5Hex(fileContent));
        fileApiDTO.setSize(fileContent.length);
        fileApiDTO.setContent(ConvertUtils.bytesToBase64(fileContent));

        requestApiDTO.setFile(fileApiDTO);

        return requestApiDTO;
    }

    private CardDocumentDTO getDecisionPdfDTO(Decision decision) {

        DocumentType documentType = documentTypeDictionaryService.getByAlias(DocumentTypeAlias.DECISION);
        byte[] decisionPdfContent = pdfService.getDecision(decision.getId(), DocumentAutoGenerationEventType.SEND_TO_MIB);

        return buildCardDocumentDTO(documentType, decisionPdfContent);
    }

    private Optional<CardDocumentDTO> getNotificationPdfDTOOpt(MibExecutionCard card) {
        Optional<Pair<byte[], DocumentType>> notificationFileOpt = notificationService.getPresetNotificationFile(card);
        return notificationFileOpt.map(p -> buildCardDocumentDTO(p.getSecond(), p.getFirst()));
    }

    private CardDocumentDTO buildCardDocumentDTO(MibCardDocument document) {
        byte[] content = fileService.getOrThrow(document.getUri());
        return buildCardDocumentDTO(document.getDocumentType(), content);
    }

    private CardDocumentDTO buildCardDocumentDTO(DocumentType documentType, byte[] content) {
        CardDocumentDTO documentDTO = new CardDocumentDTO();

        documentDTO.setDocumentName(documentType.getDefaultName());
        documentDTO.setDocumentTypeId(mibDictTransferService.transDocumentType(documentType));
        documentDTO.setContent(ConvertUtils.bytesToBase64(content));

        return documentDTO;
    }
}
