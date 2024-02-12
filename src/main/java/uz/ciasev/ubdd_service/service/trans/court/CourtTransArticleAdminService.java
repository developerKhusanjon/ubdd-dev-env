package uz.ciasev.ubdd_service.service.trans.court;

import uz.ciasev.ubdd_service.dto.internal.trans.request.court.CourtTransArticleCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;
import uz.ciasev.ubdd_service.service.trans.TransEntityCRDService;

public interface CourtTransArticleAdminService extends TransEntityCRDService<CourtTransArticle, CourtTransArticleCreateRequestDTO> {
}
