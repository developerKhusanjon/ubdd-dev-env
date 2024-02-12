package uz.ciasev.ubdd_service.utils;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.protocol.RepeatabilityPdfProjection;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FormatUtils {

    private static final String SOM = "so`m";
    private static final String YEAS = "yil";
    private static final String MONTHS = "oy";
    private static final String DAYS = "kun";
    private static final String ARREST_DAYS = "sutka";
    private static final String HOURS = "soat";

    private FormatUtils() {
    }

    public static String addressToText(@Nullable Address address) {
        return (address != null)
                ? address.getFullAddressText()
                : null;
//        return (address != null)
//                ? buildAddressText(address.getCountry(), address.getRegion(), address.getDistrict(), address.getAddress())
//                : null;
    }

    public static String buildAddressText(Country country, Region region, District district, String address) {
        return Stream.of(
                        Optional.ofNullable(country).map(Country::getDefaultName).orElse(""),
                        Optional.ofNullable(region).map(Region::getDefaultName).orElse(""),
                        Optional.ofNullable(district).map(District::getDefaultName).orElse(""),
                        Optional.ofNullable(address).orElse("")
                ).filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));
    }

    public static String mobileToText(String phone) {
        if (phone == null || phone.length() < 3) return "";
        if (phone.length() < 12) return phone;

        return String.join(
                "",
                "+",
                phone.substring(0, 3),
                " (",
                phone.substring(3, 5),
                ") ",
                phone.substring(5, 8),
                "-",
                phone.substring(8, 10),
                "-",
                phone.substring(10, 12)
        );
    }

    public static String punishmentToString(Punishment punishment) {
        return punishmentToString(punishment, true);
    }

    public static String punishmentToStringWithoutExecution(Punishment punishment) {
        return punishmentToString(punishment, false);
    }

    public static String compensationAmountText(Compensation compensation) {
        return punishmentAmountToString(compensation.getAmount(), compensation.getPaidAmount(), true);
    }

    public static String compensationAmountTextWithoutExecution(Compensation compensation) {
        return punishmentAmountToString(compensation.getAmount(), compensation.getPaidAmount(), false);
    }

    public static List<String> violationRepeatabilityToText(List<RepeatabilityPdfProjection> protocolRepeatability) {
        return protocolRepeatability
                .stream()
                .map(p -> p.getArticlePart().getShortName().getLat() + "/" + p.getViolationTime().toLocalDate())
                .collect(Collectors.toList());
    }

    public static String getDisplayNameOrDefault(AbstractDict entity, String defaultValue) {
        return Optional
                .ofNullable(entity)
                .map(AbstractDict::getDefaultName)
                .orElse(defaultValue);
    }

    public static String getDisplayNameOrDefault(AbstractDict entity) {
        return getDisplayNameOrDefault(entity, "");
    }

    public static String termToString(Integer years, Integer months, Integer days) {
        List<String> builder = new ArrayList<>();
        if (years != 0) {
            builder.add(years + " " + YEAS);
        }

        if (months != 0) {
            builder.add(months + " " + MONTHS);
        }

        if (days != 0) {
            builder.add(days + " " + DAYS);
        }

        return String.join(" ", builder);
//        return years + " " + YEAS + " " + months + " " + MONTHS + " " + days + " " + DAYS;
    }

    public static String moneyToString(Long amount) {
        if (amount == null) {
            return "";
        }

        return MoneyFormatter.coinToString(amount) + " " + SOM;
    }

    private static String punishmentToString(Punishment punishment, boolean withExecution) {
        switch (punishment.getType().getAlias()) {
            case PENALTY:
                return punishmentAmountToString(
                        punishment.getPenalty().getAmount(),
                        punishment.getPenalty().getPaidAmount(),
                        withExecution);
            case WITHDRAWAL:
                return withdrawalPunishmentPunishmentToString(punishment.getWithdrawal(), withExecution);
            case CONFISCATION:
                return confiscationPunishmentPunishmentToString(punishment.getConfiscation(), withExecution);
            case LICENSE_REVOCATION:
                return licenseRevocationPunishmentPunishmentToString(punishment.getLicenseRevocation(), withExecution);
            case ARREST:
                return arrestPunishmentToString(punishment.getArrest(), withExecution);
            case DEPORTATION:
                return deportationPunishmentPunishmentToString(punishment.getDeportation(), withExecution);
            case COMMUNITY_WORK:
                return communityWorkPunishmentToString(punishment.getCommunityWork(), withExecution);
            case MEDICAL_PENALTY:
                return medicalPunishmentToString(punishment.getMedical(), withExecution);
        }
        return "";
    }

    private static String arrestPunishmentToString(ArrestPunishment punishment, boolean withExecution) {
        return punishment.getDays() + " " + ARREST_DAYS;
    }

    private static String communityWorkPunishmentToString(CommunityWorkPunishment punishment, boolean withExecution) {
        return punishment.getHours() + " " + HOURS;
    }

    private static String deportationPunishmentPunishmentToString(DeportationPunishment punishment, boolean withExecution) {
        return termToString(punishment.getYears(), punishment.getMonths(), punishment.getDays());
    }

    private static String licenseRevocationPunishmentPunishmentToString(LicenseRevocationPunishment punishment, boolean withExecution) {
        return termToString(punishment.getYears(), punishment.getMonths(), punishment.getDays());
    }

    private static String medicalPunishmentToString(MedicalPunishment punishment, boolean withExecution) {
        return punishment.getDays() + " " + DAYS;
    }

    private static String punishmentAmountToString(Long amount, Long paidAmount, boolean withExecution) {
        StringBuilder res = new StringBuilder(moneyToString(amount));

        if (withExecution) {
            res.append(constructPaidAmountText(paidAmount));
        }
        return res.toString();
    }

    private static String withdrawalPunishmentPunishmentToString(WithdrawalPunishment punishment, boolean withExecution) {
        return moneyToString(punishment.getAmount());
    }

    private static String confiscationPunishmentPunishmentToString(ConfiscationPunishment punishment, boolean withExecution) {
        return moneyToString(punishment.getAmount());
    }

    private static String constructPaidAmountText(Long paidAmount){
        if (paidAmount == 0L) {
            return " / to`lanmagan";
        }

        return " / " + moneyToString(paidAmount) + " to`langan";
    }
}