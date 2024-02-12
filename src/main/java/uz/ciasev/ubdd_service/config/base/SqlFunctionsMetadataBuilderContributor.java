package uz.ciasev.ubdd_service.config.base;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    private final String dbSchema = "core_v0";

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "jsonb_extract_path_text",
                new SQLFunctionTemplate(
                        StandardBasicTypes.TEXT,
                        "jsonb_extract_path_text(?1, ?2)"
                )
        );

        metadataBuilder.applySqlFunction(
                "string_agg",
                new SQLFunctionTemplate(
                        StandardBasicTypes.TEXT,
                        "string_agg(?1, ?2)"
                )
        );

        metadataBuilder.applySqlFunction(
                "get_person_by_case_id",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        dbSchema + ".get_person_by_case_id(?1)"
                )
        );

        metadataBuilder.applySqlFunction(
                "get_protocol_article_parts_by_case_id",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        dbSchema + ".get_protocol_article_parts_by_case_id(?1)"
                )
        );

        metadataBuilder.applySqlFunction(
                "get_protocols_in_case_id",
                new SQLFunctionTemplate(
                        StandardBasicTypes.LONG,
                        dbSchema + ".get_protocols_in_case_id(?1)"
                )
        );

        metadataBuilder.applySqlFunction(
                "getVehicleNumberByViolator",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        dbSchema + ".get_vehicle_number_by_violator(?1)"
                )
        );

        metadataBuilder.applySqlFunction(
                "getMibCaseNumberByDecision",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        dbSchema + ".get_mib_case_number_by_decision(?1)"
                )
        );
    }
}