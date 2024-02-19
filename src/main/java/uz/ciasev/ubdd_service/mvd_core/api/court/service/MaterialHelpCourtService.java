package uz.ciasev.ubdd_service.mvd_core.api.court.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialGroupAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.court.IgnoredMaterialTypeException;
import uz.ciasev.ubdd_service.exception.court.IgnoredMaterialTypeForExistsMaterialException;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialHelpCourtService {

    private final DecisionService decisionService;
    private final InvoiceRepository invoiceRepository;
    private final DecisionRepository decisionRepository;


    public void checkIgnoredMaterialType(ThirdCourtRequest request) {
        if (isIgnoredMaterialType(request.getMaterialType())) {
            if (Objects.isNull(request.getMaterialId())) {
                throw new IgnoredMaterialTypeException(request.getMaterialType());
            } else {
                throw new IgnoredMaterialTypeForExistsMaterialException(request.getMaterialType());
            }
        }
    }


    public void checkIgnoredMaterialType(@Nullable CourtMaterialType materialType) {
        if (isIgnoredMaterialType(materialType)) {
            throw new IgnoredMaterialTypeException(materialType);
        }
    }


    public boolean isIgnoredMaterialType(@Nullable CourtMaterialType materialType) {
        if (materialType != null) {
            return materialType.getGroupAlias().equals(CourtMaterialGroupAlias.IGNORED);
        }

        return false;
    }

    public Optional<Decision> findDecisionByCourtSearchParam(String seriesInput, String number) {
        String series = Objects.requireNonNullElse(seriesInput, "");

        if (number == null) {
            throw new CourtValidationException("Number for search required");
        }

        Optional<Decision> otherParamSearch = Optional.empty();

        if ("KV".equals(series)) {
            otherParamSearch = invoiceRepository.findDecisionByPenaltyInvoiceSerial(series + number);
        } else if (series.startsWith("P")) {
            otherParamSearch = decisionRepository.findByProtocolSeriesAndNumber(series, number);
        }

        if (otherParamSearch.isPresent()) {
            return otherParamSearch;
        }

        return decisionService
                .findBySeriesAndNumber(series, number);

    }
}
