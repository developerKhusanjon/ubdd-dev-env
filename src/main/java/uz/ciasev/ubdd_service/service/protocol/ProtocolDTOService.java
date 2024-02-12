package uz.ciasev.ubdd_service.service.protocol;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.response.adm.ViolationResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.*;
import uz.ciasev.ubdd_service.entity.protocol.*;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.function.Supplier;

@Validated
public interface ProtocolDTOService {

    ProtocolDetailResponseDTO buildDetail(User user, Supplier<Protocol> supplier);

    ProtocolDetailResponseDTO buildDetailForCreateProtocol(User user, Supplier<Protocol> supplier);

    List<RegisteredProtocolListDTO> buildRegisteredList(Supplier<List<ProtocolSimpleListProjection>> supplier);

    List<ProtocolFullListResponseDTO> buildFullList(Supplier<List<ProtocolFullListProjection>> supplier);

    Page<ProtocolFullListResponseDTO> buildFullPage(Supplier<Page<ProtocolFullListProjection>> supplier);

    Page<ProtocolUbddListResponseDTO> buildUbddPage(Supplier<Page<ProtocolUbddListView>> supplier);

    List<ViolationResponseDTO> buildViolationList(Supplier<List<ViolationListView>> supplier);

    List<ArticleResponseDTO> buildArticleList(Supplier<List<ProtocolArticle>> supplier);

    ProtocolDetailResponseDTO convertToDetailDTO(User user, Protocol protocol);

    ProtocolDetailResponseDTO convertToDetailDTOForCreateProtocol(User user, Protocol protocol);

    @Deprecated
    ProtocolDetailResponseDTO convertToSingleResponseDTO(Protocol protocol);
}
