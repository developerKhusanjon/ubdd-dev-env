package uz.ciasev.ubdd_service.utils;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationArticleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    public static LocalDate dateToLocalDate(Date date) {
        return (date != null)
               ? date.toLocalDate()
               : null;
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp violationTime) {
        return (violationTime != null)
               ? violationTime.toLocalDateTime()
               : null;
    }

    public static Long bigDecimalToLong(BigDecimal value) {
        return (value != null)
               ? value.longValue()
               : null;
    }

    public static Long integerToLong(Integer value) {
        return (value != null)
               ? value.longValue()
               : null;
    }

    public static LocalDate localDateTimeToLocalDate(LocalDateTime value) {
        return (value != null)
               ? value.toLocalDate()
               : null;
    }

    public static String bytesToBase64(byte[] value) {
        return (value != null)
               ? Base64.getEncoder().encodeToString(value)
               : null;
    }

    public static byte[] base64ToBytes(String value) {
        return (value != null)
                ? Base64.getDecoder().decode(value)
                : null;
    }

    public static List<ArticleResponseDTO> jsonArticleToDTO(List<ArticlePairJson> value) {
        return (value != null)
               ? value.stream().map(ArticleResponseDTO::new).collect(Collectors.toList())
               : List.of();
    }

    public static List<ArticleResponseDTO> protocolArticleToDTO(List<ProtocolArticle> value) {
        return (value != null)
               ? value.stream().map(ArticleResponseDTO::new).collect(Collectors.toList())
               : List.of();
    }

    public static List<ArticlePairJson> protocolArticleDTOToJson(List<QualificationArticleRequestDTO> value) {
        return (value != null)
               ? value.stream().map(QualificationArticleRequestDTO::buildArticlePairJson).collect(Collectors.toList())
               : List.of();
    }

    public static String getUniqueId(String string, Long id) {
        return string + "_" + id;
    }
}
