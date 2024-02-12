package uz.ciasev.ubdd_service.service.resolution.compensation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.CompensationDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.CompensationListResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.mib.MibCardDTOService;
import uz.ciasev.ubdd_service.service.mib.MibCardService;
import uz.ciasev.ubdd_service.service.victim.VictimService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompensationDTOServiceImpl implements CompensationDTOService {

    private final CompensationService compensationService;
    private final VictimService victimService;
    private final MibCardService mibCardService;
    private final MibCardDTOService mibCardDTOService;
    private final ProtocolRepository protocolRepository;


    @Override
    public CompensationDetailResponseDTO findDetailById(Long id) {
        Compensation compensation = compensationService.findById(id);
        MibCardResponseDTO mibExecutionCard = mibCardService.findByCompensationId(id)
                .map(mibCardDTOService::convertToDetailDTO)
                .orElse(null);

        return convertToDTO(compensation, mibExecutionCard);
    }

//    @Override
//    public List<CompensationListResponseDTO> findAllDTOByFilters(Long resolutionId,
//                                                                 Long decisionId,
//                                                                 Long violatorId,
//                                                                 Long victimId) {
//        return compensationService.findAllByFilters(
//                        resolutionId,
//                        decisionId,
//                        violatorId,
//                        victimId
//                )
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<CompensationListResponseDTO> findAllDTOByDecisionId(Long decisionId) {
        return compensationService.findAllByDecisionId(decisionId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompensationListResponseDTO convertToDTO(Compensation compensation) {
        CompensationListResponseDTO responseDTO = null;


        if (compensation != null) {

            Violator violator = compensationService.findViolatorByCompensationId(compensation.getId());
            VictimListResponseDTO victimResponseDTO = Optional.ofNullable(compensation.getVictimId()).map(victimService::findListDTOById).orElse(null);
//            VictimListResponseDTO victimResponseDTO = null;
//            if (compensation.getVictim() != null) {
//                victimResponseDTO = new VictimListResponseDTO(
//                        compensation.getVictim(),
//                        compensation.getVictim().getPerson()
//                );
//            }

            responseDTO = new CompensationListResponseDTO(
                    compensation,
                    violator,
                    violator.getPerson(),
                    victimResponseDTO,
                    compensation.getInvoiceOptional().map(InvoiceResponseDTO::new).orElse(null)
            );
        }

        return responseDTO;
    }

    private CompensationDetailResponseDTO convertToDTO(Compensation compensation, MibCardResponseDTO minCard) {
        CompensationDetailResponseDTO responseDTO = null;
        if (compensation != null) {

            Violator violator = compensationService.findViolatorByCompensationId(compensation.getId());
            VictimListResponseDTO victimResponseDTO = Optional.ofNullable(compensation.getVictimId()).map(victimService::findListDTOById).orElse(null);
//            if (compensation.getVictim() != null) {
//                victimResponseDTO = new VictimListResponseDTO(
//                        compensation.getVictim(),
//                        compensation.getVictim().getPerson()
//                );
//            }

            responseDTO = new CompensationDetailResponseDTO(
                    compensation,
                    violator,
                    violator.getPerson(),
                    victimResponseDTO,
                    compensation.getInvoiceOptional().map(InvoiceResponseDTO::new).orElse(null),
                    minCard
            );
        }

        return responseDTO;
    }
}
