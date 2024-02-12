package uz.ciasev.ubdd_service.service.sit_center.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;

import java.time.LocalDateTime;

@Getter
public class SitCenterWebhookEventWantedProtocolDataDTO {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime protocolRegistrationTime;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime protocolUpdateTime;
    private final String protocolSeries;
    private final Long protocolNumber;
    private final Long wantedId;
    private final Long protocolOrganId;
    private final Long protocolRegionId;
    private final Long protocolDistrictId;
    private final String wantedType;
    private final String pinpp;
    private final String lastName;
    private final String firstName;
    private final String secondName;
    private final String birthDate;
    private final String articles;
    private final String wantedCountry;
    private final String searchCaseNumber;
    private final String searchCaseDate;
    private final String searchInitiatorHead;
    private final String searchInitiatorDepartment;
    private final String searchReason;
    private final Double protocolLatitude;
    private final Double protocolLongitude;
    private final String protocolInspectorInfo;
    private final String inspectorPinpp;

    public SitCenterWebhookEventWantedProtocolDataDTO(WantedProtocol wp) {
        this.protocolRegistrationTime = wp.getProtocol().getRegistrationTime();
        this.protocolSeries = wp.getProtocol().getSeries();
        this.protocolNumber = Long.parseLong(wp.getProtocol().getNumber());
        this.wantedId = wp.getId();
        this.protocolOrganId = wp.getProtocol().getOrganId();
        this.protocolRegionId = wp.getProtocol().getRegionId();
        this.protocolDistrictId = wp.getProtocol().getDistrictId();
        this.wantedType = wp.getType().name();
        this.pinpp = String.valueOf(wp.getPinpp());
        this.lastName = wp.getLastName();
        this.secondName = wp.getSecondName();
        this.firstName = wp.getFirstName();
        this.birthDate = wp.getBirthDate();
        this.articles = wp.getArticles();
        this.wantedCountry = wp.getWantedCountry();
        this.searchCaseNumber = wp.getSearchCaseNumber();
        this.searchCaseDate = wp.getSearchCaseDate();
        this.searchInitiatorHead = wp.getSearchInitiatorHead();
        this.searchInitiatorDepartment = wp.getSearchInitiatorDepartment();
        this.searchReason = wp.getSearchReason();
        this.protocolLatitude = wp.getProtocol().getLatitude();
        this.protocolLongitude = wp.getProtocol().getLongitude();
        this.protocolInspectorInfo = wp.getProtocol().getInspectorInfo();
        this.inspectorPinpp = wp.getInspectorPinpp();
        this.protocolUpdateTime = wp.getProtocol().getEditedTime();
    }
}
