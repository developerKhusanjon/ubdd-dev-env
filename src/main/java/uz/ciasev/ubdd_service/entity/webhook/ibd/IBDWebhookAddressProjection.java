package uz.ciasev.ubdd_service.entity.webhook.ibd;

public interface IBDWebhookAddressProjection {
    String getFullAddressText();
    Long getCountryId();
    Long getRegionId();
    Long getDistrictId();
}
