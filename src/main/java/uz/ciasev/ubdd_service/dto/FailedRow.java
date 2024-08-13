package uz.ciasev.ubdd_service.dto;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;

import java.util.Map;

@ExcelFile(name = "Failed_Rows")
public class FailedRow {
    private String protocol_externalId;
    private String protocol_inspectorRegionId;
    private String protocol_inspectorDistrictId;
    private String protocol_inspectorPositionId;
    private String protocol_inspectorRankId;
    private String protocol_inspectorFio;
    private String protocol_inspectorInfo;
    private String protocol_inspectorWorkCertificate;
    private String protocol_registrationTime;
    private String protocol_violationTime;
    private String protocol_articleId;
    private String protocol_articlePartId;
    private String protocol_fabula;
    private String protocol_regionId;
    private String protocol_districtId;
    private String protocol_mtpId;
    private String mbprotocol_address;
    private String protocol_isFamiliarize;
    private String protocol_isAgree;
    private String protocol_violator_pinpp;
    private String protocol_violator_mobile;
    private String protocol_violator_landline;
    private String protocol_violator_actualAddress_countryId;
    private String protocol_violator_actualAddress_regionId;
    private String protocol_violator_actualAddress_districtId;
    private String protocol_violator_actualAddress_address;
    private String protocol_violator_postAddress_countryId;
    private String protocol_violator_postAddress_regionId;
    private String protocol_violator_postAddress_districtId;
    private String protocol_violator_postAddress_address;
    private String protocol_violator_violatorDetail_occupationId;
    private String protocol_violator_violatorDetail_employmentPlace;
    private String protocol_violator_violatorDetail_employmentPosition;
    private String protocol_violator_violatorDetail_additionally;
    private String protocol_violator_violatorDetail_signature;
    private String resolution_externalId;
    private String resolution_resolutionTime;
    private String resolution_isArticle33;
    private String resolution_isArticle34;
    private String resolution_departmentId;
    private String resolution_regionId;
    private String resolution_districtId;
    private String resolution_signature;
    private String resolution_decisionTypeId;
    private String resolution_articleId;
    private String resolution_articlePartId;
    private String resolution_articleViolationTypeId;
    private String resolution_executionFromDate;
    private String resolution_mainPunishment_punishmentTypeId;
    private String resolution_mainPunishment_amount;
    private String inovice_isDiscount70;
    private String inovice_isDiscount50;
    private String inovice_externalId;
    private String inovice_invoiceId;
    private String inovice_invoiceSerial;
    private String inovice_invoiceNumber;
    private String inovice_invoiceDate;
    private String inovice_discount70ForDate;
    private String inovice_discount50ForDate;
    private String inovice_penaltyPunishmentAmount;
    private String inovice_discount70Amount;
    private String inovice_discount50Amount;
    private String inovice_organName;
    private String inovice_bankInn;
    private String inovice_bankName;
    private String inovice_bankCode;
    private String inovice_bankAccount;
    private String inovice_payerName;
    private String inovice_payerAddress;
    private String inovice_payerBirthdate;
    private String payments_id;
    private String payments_externalId;
    private String payments_invoiceSerial;
    private String payments_bid;
    private String payments_amount;
    private String payments_docNumber;
    private String payments_paidAt;
    private String payments_payerInfo_fromBankCode;
    private String payments_payerInfo_fromBankAccount;
    private String payments_payerInfo_fromBankName;
    private String payments_payerInfo_fromInn;
    private String payments_payeeInfo_toBankCode;
    private String payments_payeeInfo_toBankAccount;
    private String payments_payeeInfo_toBankName;
    private String payments_payeeInfo_toInn;

    public FailedRow() {}

    public FailedRow(Map<String, String> values) {
        this.protocol_externalId = values.getOrDefault("protocol_externalId", null);
        this.protocol_inspectorRegionId = values.getOrDefault("protocol_inspectorRegionId", null);
        this.protocol_inspectorDistrictId = values.getOrDefault("protocol_inspectorDistrictId", null);
        this.protocol_inspectorPositionId = values.getOrDefault("protocol_inspectorPositionId", null);
        this.protocol_inspectorRankId = values.getOrDefault("protocol_inspectorRankId", null);
        this.protocol_inspectorFio = values.getOrDefault("protocol_inspectorFio", null);
        this.protocol_inspectorInfo = values.getOrDefault("protocol_inspectorInfo", null);
        this.protocol_inspectorWorkCertificate = values.getOrDefault("protocol_inspectorWorkCertificate", null);
        this.protocol_registrationTime = values.getOrDefault("protocol_registrationTime", null);
        this.protocol_violationTime = values.getOrDefault("protocol_violationTime", null);
        this.protocol_articleId = values.getOrDefault("protocol_articleId", null);
        this.protocol_articlePartId = values.getOrDefault("protocol_articlePartId", null);
        this.protocol_fabula = values.getOrDefault("protocol_fabula", null);
        this.protocol_regionId = values.getOrDefault("protocol_regionId", null);
        this.protocol_districtId = values.getOrDefault("protocol_districtId", null);
        this.protocol_mtpId = values.getOrDefault("protocol_mtpId", null);
        this.mbprotocol_address = values.getOrDefault("mbprotocol_address", null);
        this.protocol_isFamiliarize = values.getOrDefault("protocol_isFamiliarize", null);
        this.protocol_isAgree = values.getOrDefault("protocol_isAgree", null);
        this.protocol_violator_pinpp = values.getOrDefault("protocol_violator_pinpp", null);
        this.protocol_violator_mobile = values.getOrDefault("protocol_violator_mobile", null);
        this.protocol_violator_landline = values.getOrDefault("protocol_violator_landline", null);
        this.protocol_violator_actualAddress_countryId = values.getOrDefault("protocol_violator_actualAddress_countryId", null);
        this.protocol_violator_actualAddress_regionId = values.getOrDefault("protocol_violator_actualAddress_regionId", null);
        this.protocol_violator_actualAddress_districtId = values.getOrDefault("protocol_violator_actualAddress_districtId", null);
        this.protocol_violator_actualAddress_address = values.getOrDefault("protocol_violator_actualAddress_address", null);
        this.protocol_violator_postAddress_countryId = values.getOrDefault("protocol_violator_postAddress_countryId", null);
        this.protocol_violator_postAddress_regionId = values.getOrDefault("protocol_violator_postAddress_regionId", null);
        this.protocol_violator_postAddress_districtId = values.getOrDefault("protocol_violator_postAddress_districtId", null);
        this.protocol_violator_postAddress_address = values.getOrDefault("protocol_violator_postAddress_address", null);
        this.protocol_violator_violatorDetail_occupationId = values.getOrDefault("protocol_violator_violatorDetail_occupationId", null);
        this.protocol_violator_violatorDetail_employmentPlace = values.getOrDefault("protocol_violator_violatorDetail_employmentPlace", null);
        this.protocol_violator_violatorDetail_employmentPosition = values.getOrDefault("protocol_violator_violatorDetail_employmentPosition", null);
        this.protocol_violator_violatorDetail_additionally = values.getOrDefault("protocol_violator_violatorDetail_additionally", null);
        this.protocol_violator_violatorDetail_signature = values.getOrDefault("protocol_violator_violatorDetail_signature", null);
        this.resolution_externalId = values.getOrDefault("resolution_externalId", null);
        this.resolution_resolutionTime = values.getOrDefault("resolution_resolutionTime", null);
        this.resolution_isArticle33 = values.getOrDefault("resolution_isArticle33", null);
        this.resolution_isArticle34 = values.getOrDefault("resolution_isArticle34", null);
        this.resolution_departmentId = values.getOrDefault("resolution_departmentId", null);
        this.resolution_regionId = values.getOrDefault("resolution_regionId", null);
        this.resolution_districtId = values.getOrDefault("resolution_districtId", null);
        this.resolution_signature = values.getOrDefault("resolution_signature", null);
        this.resolution_decisionTypeId = values.getOrDefault("resolution_decisionTypeId", null);
        this.resolution_articleId = values.getOrDefault("resolution_articleId", null);
        this.resolution_articlePartId = values.getOrDefault("resolution_articlePartId", null);
        this.resolution_articleViolationTypeId = values.getOrDefault("resolution_articleViolationTypeId", null);
        this.resolution_executionFromDate = values.getOrDefault("resolution_executionFromDate", null);
        this.resolution_mainPunishment_punishmentTypeId = values.getOrDefault("resolution_mainPunishment_punishmentTypeId", null);
        this.resolution_mainPunishment_amount = values.getOrDefault("resolution_mainPunishment_amount", null);
        this.inovice_isDiscount70 = values.getOrDefault("inovice_isDiscount70", null);
        this.inovice_isDiscount50 = values.getOrDefault("inovice_isDiscount50", null);
        this.inovice_externalId = values.getOrDefault("inovice_externalId", null);
        this.inovice_invoiceId = values.getOrDefault("inovice_invoiceId", null);
        this.inovice_invoiceSerial = values.getOrDefault("inovice_invoiceSerial", null);
        this.inovice_invoiceNumber = values.getOrDefault("inovice_invoiceNumber", null);
        this.inovice_invoiceDate = values.getOrDefault("inovice_invoiceDate", null);
        this.inovice_discount70ForDate = values.getOrDefault("inovice_discount70ForDate", null);
        this.inovice_discount50ForDate = values.getOrDefault("inovice_discount50ForDate", null);
        this.inovice_penaltyPunishmentAmount = values.getOrDefault("inovice_penaltyPunishmentAmount", null);
        this.inovice_discount70Amount = values.getOrDefault("inovice_discount70Amount", null);
        this.inovice_discount50Amount = values.getOrDefault("inovice_discount50Amount", null);
        this.inovice_organName = values.getOrDefault("inovice_organName", null);
        this.inovice_bankInn = values.getOrDefault("inovice_bankInn", null);
        this.inovice_bankName = values.getOrDefault("inovice_bankName", null);
        this.inovice_bankCode = values.getOrDefault("inovice_bankCode", null);
        this.inovice_bankAccount = values.getOrDefault("inovice_bankAccount", null);
        this.inovice_payerName = values.getOrDefault("inovice_payerName", null);
        this.inovice_payerAddress = values.getOrDefault("inovice_payerAddress", null);
        this.inovice_payerBirthdate = values.getOrDefault("inovice_payerBirthdate", null);
        this.payments_id = values.getOrDefault("payments_id", null);
        this.payments_externalId = values.getOrDefault("payments_externalId", null);
        this.payments_invoiceSerial = values.getOrDefault("payments_invoiceSerial", null);
        this.payments_bid = values.getOrDefault("payments_bid", null);
        this.payments_amount = values.getOrDefault("payments_amount", null);
        this.payments_docNumber = values.getOrDefault("payments_docNumber", null);
        this.payments_paidAt = values.getOrDefault("payments_paidAt", null);
        this.payments_payerInfo_fromBankCode = values.getOrDefault("payments_payerInfo_fromBankCode", null);
        this.payments_payerInfo_fromBankAccount = values.getOrDefault("payments_payerInfo_fromBankAccount", null);
        this.payments_payerInfo_fromBankName = values.getOrDefault("payments_payerInfo_fromBankName", null);
        this.payments_payerInfo_fromInn = values.getOrDefault("payments_payerInfo_fromInn", null);
        this.payments_payeeInfo_toBankCode = values.getOrDefault("payments_payeeInfo_toBankCode", null);
        this.payments_payeeInfo_toBankAccount = values.getOrDefault("payments_payeeInfo_toBankAccount", null);
        this.payments_payeeInfo_toBankName = values.getOrDefault("payments_payeeInfo_toBankName", null);
        this.payments_payeeInfo_toInn = values.getOrDefault("payments_payeeInfo_toInn", null);
    }

    @ExcelColumn(name = "protocol_externalId", order = 1)
    public String getProtocol_externalId() {
        return protocol_externalId;
    }

    @ExcelColumn(name = "protocol_inspectorRegionId", order = 2)
    public String getProtocol_inspectorRegionId() {
        return protocol_inspectorRegionId;
    }

    @ExcelColumn(name = "protocol_inspectorDistrictId", order = 3)
    public String getProtocol_inspectorDistrictId() {
        return protocol_inspectorDistrictId;
    }

    @ExcelColumn(name = "protocol_inspectorPositionId", order = 4)
    public String getProtocol_inspectorPositionId() {
        return protocol_inspectorPositionId;
    }

    @ExcelColumn(name = "protocol_inspectorRankId", order = 5)
    public String getProtocol_inspectorRankId() {
        return protocol_inspectorRankId;
    }

    @ExcelColumn(name = "protocol_inspectorFio", order = 6)
    public String getProtocol_inspectorFio() {
        return protocol_inspectorFio;
    }

    @ExcelColumn(name = "protocol_inspectorInfo", order = 7)
    public String getProtocol_inspectorInfo() {
        return protocol_inspectorInfo;
    }

    @ExcelColumn(name = "protocol_inspectorWorkCertificate", order = 8)
    public String getProtocol_inspectorWorkCertificate() {
        return protocol_inspectorWorkCertificate;
    }

    @ExcelColumn(name = "protocol_registrationTime", order = 9)
    public String getProtocol_registrationTime() {
        return protocol_registrationTime;
    }

    @ExcelColumn(name = "protocol_violationTime", order = 10)
    public String getProtocol_violationTime() {
        return protocol_violationTime;
    }

    @ExcelColumn(name = "protocol_articleId", order = 11)
    public String getProtocol_articleId() {
        return protocol_articleId;
    }

    @ExcelColumn(name = "protocol_articlePartId", order = 12)
    public String getProtocol_articlePartId() {
        return protocol_articlePartId;
    }

    @ExcelColumn(name = "protocol_fabula", order = 13)
    public String getProtocol_fabula() {
        return protocol_fabula;
    }

    @ExcelColumn(name = "protocol_regionId", order = 14)
    public String getProtocol_regionId() {
        return protocol_regionId;
    }

    @ExcelColumn(name = "protocol_districtId", order = 15)
    public String getProtocol_districtId() {
        return protocol_districtId;
    }

    @ExcelColumn(name = "protocol_mtpId", order = 16)
    public String getProtocol_mtpId() {
        return protocol_mtpId;
    }

    @ExcelColumn(name = "mbprotocol_address", order = 17)
    public String getMbprotocol_address() {
        return mbprotocol_address;
    }

    @ExcelColumn(name = "protocol_isFamiliarize", order = 18)
    public String getProtocol_isFamiliarize() {
        return protocol_isFamiliarize;
    }

    @ExcelColumn(name = "protocol_isAgree", order = 19)
    public String getProtocol_isAgree() {
        return protocol_isAgree;
    }

    @ExcelColumn(name = "protocol_violator_pinpp", order = 20)
    public String getProtocol_violator_pinpp() {
        return protocol_violator_pinpp;
    }

    @ExcelColumn(name = "protocol_violator_mobile", order = 21)
    public String getProtocol_violator_mobile() {
        return protocol_violator_mobile;
    }

    @ExcelColumn(name = "protocol_violator_landline", order = 22)
    public String getProtocol_violator_landline() {
        return protocol_violator_landline;
    }

    @ExcelColumn(name = "protocol_violator_actualAddress_countryId", order = 23)
    public String getProtocol_violator_actualAddress_countryId() {
        return protocol_violator_actualAddress_countryId;
    }

    @ExcelColumn(name = "protocol_violator_actualAddress_regionId", order = 24)
    public String getProtocol_violator_actualAddress_regionId() {
        return protocol_violator_actualAddress_regionId;
    }

    @ExcelColumn(name = "protocol_violator_actualAddress_districtId", order = 25)
    public String getProtocol_violator_actualAddress_districtId() {
        return protocol_violator_actualAddress_districtId;
    }

    @ExcelColumn(name = "protocol_violator_actualAddress_address", order = 26)
    public String getProtocol_violator_actualAddress_address() {
        return protocol_violator_actualAddress_address;
    }

    @ExcelColumn(name = "protocol_violator_postAddress_countryId", order = 27)
    public String getProtocol_violator_postAddress_countryId() {
        return protocol_violator_postAddress_countryId;
    }

    @ExcelColumn(name = "protocol_violator_postAddress_regionId", order = 28)
    public String getProtocol_violator_postAddress_regionId() {
        return protocol_violator_postAddress_regionId;
    }

    @ExcelColumn(name = "protocol_violator_postAddress_districtId", order = 29)
    public String getProtocol_violator_postAddress_districtId() {
        return protocol_violator_postAddress_districtId;
    }

    @ExcelColumn(name = "protocol_violator_postAddress_address", order = 30)
    public String getProtocol_violator_postAddress_address() {
        return protocol_violator_postAddress_address;
    }

    @ExcelColumn(name = "protocol_violator_violatorDetail_occupationId", order = 31)
    public String getProtocol_violator_violatorDetail_occupationId() {
        return protocol_violator_violatorDetail_occupationId;
    }

    @ExcelColumn(name = "protocol_violator_violatorDetail_employmentPlace", order = 32)
    public String getProtocol_violator_violatorDetail_employmentPlace() {
        return protocol_violator_violatorDetail_employmentPlace;
    }

    @ExcelColumn(name = "protocol_violator_violatorDetail_employmentPosition", order = 33)
    public String getProtocol_violator_violatorDetail_employmentPosition() {
        return protocol_violator_violatorDetail_employmentPosition;
    }

    @ExcelColumn(name = "protocol_violator_violatorDetail_additionally", order = 34)
    public String getProtocol_violator_violatorDetail_additionally() {
        return protocol_violator_violatorDetail_additionally;
    }

    @ExcelColumn(name = "protocol_violator_violatorDetail_signature", order = 35)
    public String getProtocol_violator_violatorDetail_signature() {
        return protocol_violator_violatorDetail_signature;
    }

    @ExcelColumn(name = "resolution_externalId", order = 36)
    public String getResolution_externalId() {
        return resolution_externalId;
    }

    @ExcelColumn(name = "resolution_resolutionTime", order = 37)
    public String getResolution_resolutionTime() {
        return resolution_resolutionTime;
    }

    @ExcelColumn(name = "resolution_isArticle33", order = 38)
    public String getResolution_isArticle33() {
        return resolution_isArticle33;
    }

    @ExcelColumn(name = "resolution_isArticle34", order = 39)
    public String getResolution_isArticle34() {
        return resolution_isArticle34;
    }

    @ExcelColumn(name = "resolution_departmentId", order = 40)
    public String getResolution_departmentId() {
        return resolution_departmentId;
    }

    @ExcelColumn(name = "resolution_regionId", order = 41)
    public String getResolution_regionId() {
        return resolution_regionId;
    }

    @ExcelColumn(name = "resolution_districtId", order = 42)
    public String getResolution_districtId() {
        return resolution_districtId;
    }

    @ExcelColumn(name = "resolution_signature", order = 43)
    public String getResolution_signature() {
        return resolution_signature;
    }

    @ExcelColumn(name = "resolution_decisionTypeId", order = 44)
    public String getResolution_decisionTypeId() {
        return resolution_decisionTypeId;
    }

    @ExcelColumn(name = "resolution_articleId", order = 45)
    public String getResolution_articleId() {
        return resolution_articleId;
    }

    @ExcelColumn(name = "resolution_articlePartId", order = 46)
    public String getResolution_articlePartId() {
        return resolution_articlePartId;
    }

    @ExcelColumn(name = "resolution_articleViolationTypeId", order = 47)
    public String getResolution_articleViolationTypeId() {
        return resolution_articleViolationTypeId;
    }

    @ExcelColumn(name = "resolution_executionFromDate", order = 48)
    public String getResolution_executionFromDate() {
        return resolution_executionFromDate;
    }

    @ExcelColumn(name = "resolution_mainPunishment_punishmentTypeId", order = 49)
    public String getResolution_mainPunishment_punishmentTypeId() {
        return resolution_mainPunishment_punishmentTypeId;
    }

    @ExcelColumn(name = "resolution_mainPunishment_amount", order = 50)
    public String getResolution_mainPunishment_amount() {
        return resolution_mainPunishment_amount;
    }

    @ExcelColumn(name = "inovice_isDiscount70", order = 51)
    public String getInovice_isDiscount70() {
        return inovice_isDiscount70;
    }

    @ExcelColumn(name = "inovice_isDiscount50", order = 52)
    public String getInovice_isDiscount50() {
        return inovice_isDiscount50;
    }

    @ExcelColumn(name = "inovice_externalId", order = 53)
    public String getInovice_externalId() {
        return inovice_externalId;
    }

    @ExcelColumn(name = "inovice_invoiceId", order = 54)
    public String getInovice_invoiceId() {
        return inovice_invoiceId;
    }

    @ExcelColumn(name = "inovice_invoiceSerial", order = 55)
    public String getInovice_invoiceSerial() {
        return inovice_invoiceSerial;
    }

    @ExcelColumn(name = "inovice_invoiceNumber", order = 56)
    public String getInovice_invoiceNumber() {
        return inovice_invoiceNumber;
    }

    @ExcelColumn(name = "inovice_invoiceDate", order = 57)
    public String getInovice_invoiceDate() {
        return inovice_invoiceDate;
    }

    @ExcelColumn(name = "inovice_discount70ForDate", order = 58)
    public String getInovice_discount70ForDate() {
        return inovice_discount70ForDate;
    }

    @ExcelColumn(name = "inovice_discount50ForDate", order = 59)
    public String getInovice_discount50ForDate() {
        return inovice_discount50ForDate;
    }

    @ExcelColumn(name = "inovice_penaltyPunishmentAmount", order = 60)
    public String getInovice_penaltyPunishmentAmount() {
        return inovice_penaltyPunishmentAmount;
    }

    @ExcelColumn(name = "inovice_discount70Amount", order = 61)
    public String getInovice_discount70Amount() {
        return inovice_discount70Amount;
    }

    @ExcelColumn(name = "inovice_discount50Amount", order = 62)
    public String getInovice_discount50Amount() {
        return inovice_discount50Amount;
    }

    @ExcelColumn(name = "inovice_organName", order = 63)
    public String getInovice_organName() {
        return inovice_organName;
    }

    @ExcelColumn(name = "inovice_bankInn", order = 64)
    public String getInovice_bankInn() {
        return inovice_bankInn;
    }

    @ExcelColumn(name = "inovice_bankName", order = 65)
    public String getInovice_bankName() {
        return inovice_bankName;
    }

    @ExcelColumn(name = "inovice_bankCode", order = 66)
    public String getInovice_bankCode() {
        return inovice_bankCode;
    }

    @ExcelColumn(name = "inovice_bankAccount", order = 67)
    public String getInovice_bankAccount() {
        return inovice_bankAccount;
    }

    @ExcelColumn(name = "inovice_payerName", order = 68)
    public String getInovice_payerName() {
        return inovice_payerName;
    }

    @ExcelColumn(name = "inovice_payerAddress", order = 69)
    public String getInovice_payerAddress() {
        return inovice_payerAddress;
    }

    @ExcelColumn(name = "inovice_payerBirthdate", order = 70)
    public String getInovice_payerBirthdate() {
        return inovice_payerBirthdate;
    }

    @ExcelColumn(name = "payments_id", order = 71)
    public String getPayments_id() {
        return payments_id;
    }

    @ExcelColumn(name = "payments_externalId", order = 72)
    public String getPayments_externalId() {
        return payments_externalId;
    }

    @ExcelColumn(name = "payments_invoiceSerial", order = 73)
    public String getPayments_invoiceSerial() {
        return payments_invoiceSerial;
    }

    @ExcelColumn(name = "payments_bid", order = 74)
    public String getPayments_bid() {
        return payments_bid;
    }

    @ExcelColumn(name = "payments_amount", order = 75)
    public String getPayments_amount() {
        return payments_amount;
    }

    @ExcelColumn(name = "payments_docNumber", order = 76)
    public String getPayments_docNumber() {
        return payments_docNumber;
    }

    @ExcelColumn(name = "payments_paidAt", order = 77)
    public String getPayments_paidAt() {
        return payments_paidAt;
    }

    @ExcelColumn(name = "payments_payerInfo_fromBankCode", order = 78)
    public String getPayments_payerInfo_fromBankCode() {
        return payments_payerInfo_fromBankCode;
    }

    @ExcelColumn(name = "payments_payerInfo_fromBankAccount", order = 79)
    public String getPayments_payerInfo_fromBankAccount() {
        return payments_payerInfo_fromBankAccount;
    }

    @ExcelColumn(name = "payments_payerInfo_fromBankName", order = 80)
    public String getPayments_payerInfo_fromBankName() {
        return payments_payerInfo_fromBankName;
    }

    @ExcelColumn(name = "payments_payerInfo_fromInn", order = 81)
    public String getPayments_payerInfo_fromInn() {
        return payments_payerInfo_fromInn;
    }

    @ExcelColumn(name = "payments_payeeInfo_toBankCode", order = 82)
    public String getPayments_payeeInfo_toBankCode() {
        return payments_payeeInfo_toBankCode;
    }

    @ExcelColumn(name = "payments_payeeInfo_toBankAccount", order = 83)
    public String getPayments_payeeInfo_toBankAccount() {
        return payments_payeeInfo_toBankAccount;
    }

    @ExcelColumn(name = "payments_payeeInfo_toBankName", order = 84)
    public String getPayments_payeeInfo_toBankName() {
        return payments_payeeInfo_toBankName;
    }

    @ExcelColumn(name = "payments_payeeInfo_toInn", order = 85)
    public String getPayments_payeeInfo_toInn() {
        return payments_payeeInfo_toInn;
    }
}