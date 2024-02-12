package uz.ciasev.ubdd_service.service.court.methods;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourtAddress {

    private Long countryId;

    private Long regionId;

    private Long districtId;

    private String address;

    private Boolean isUzbekistan;
}