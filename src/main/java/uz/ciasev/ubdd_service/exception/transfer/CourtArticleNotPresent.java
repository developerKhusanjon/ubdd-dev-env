package uz.ciasev.ubdd_service.exception.transfer;

import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;

import javax.annotation.Nullable;

public class CourtArticleNotPresent extends DictTransferNotPresent {

    private final Long value;

    public CourtArticleNotPresent(@Nullable Long internalArticlePartId) {
        super(
                CourtTransArticle.class,
                String.format(
                        "internalArticlePartId=%s",
                        internalArticlePartId
                )
        );
        this.value = internalArticlePartId;
    }

    @Override
    public String getCode() {
        return String.format("%s_FOR_%s", super.getCode(), value);
    }
}
