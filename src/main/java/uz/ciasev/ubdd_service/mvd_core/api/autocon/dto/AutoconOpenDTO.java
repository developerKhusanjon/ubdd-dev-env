package uz.ciasev.ubdd_service.mvd_core.api.autocon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;

@Getter
public class AutoconOpenDTO extends AutoconDTO {

//    @JsonProperty("pWatchlist")
//    @Builder.Default
//    private Long watchList = 1L;

    @JsonProperty("pCarNumber")
    private final String vehicleNumber;

    @JsonProperty("pNote")
    private final AutoconOpenNoteDTO note;

    public AutoconOpenDTO(AutoconSending sending, String vehicleNumber, AutoconOpenNoteDTO noteDTO) {
        super(sending);
        this.vehicleNumber = vehicleNumber;
        this.note = noteDTO;
    }

//    public AutoconOpenDTO(Punishment punishment, Decision decision, Optional<UbddTexPassDataAbstract> ubddTexPassDataOpt, Function<Long, Long> findTransRegionId) {
//
//        this.punishmentId = punishment.getId();
////        this.vehicleNumber = Optional.ofNullable(ubddTexPassData).map(UbddTexPassData::getVehicleNumber).orElse(null);
//
//        this.note = new AutoconOpenNoteDTO();
//
//        if (ubddTexPassDataOpt.isPresent()) {
//            UbddTexPassDataAbstract ubddTexPassData = ubddTexPassDataOpt.get();
//            this.note.owner = ubddTexPassData.getOwnerInfo();
//            this.note.model = ubddTexPassData.getVehicleModel();
//        }
//
//        this.note.dateTime = decision.getResolution().getResolutionTime();
//        this.note.decisionSeriesNumber = decision.getSeries() + decision.getNumber();
//        this.note.amount = punishment.getAmount().toString();
//        this.note.articleName = decision.getArticle().getName().getLat();
//        this.note.regionId = findTransRegionId.apply(decision.getResolution().getRegionId());
//    }

}
