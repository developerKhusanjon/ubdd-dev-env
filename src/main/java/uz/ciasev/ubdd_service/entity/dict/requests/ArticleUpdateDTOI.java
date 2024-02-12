package uz.ciasev.ubdd_service.entity.dict.requests;

public interface ArticleUpdateDTOI extends DictUpdateDTOI {
    Integer getNumber();

    Integer getPrim();

    default String getCode() {
        // Код генериться и стеиться атвоматом в энтите
        return "";
    }
}
