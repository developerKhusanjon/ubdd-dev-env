package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.CriminalCaseTransferResultTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_criminal_case_transfer_result_type")
@NoArgsConstructor
@JsonDeserialize(using = CriminalCaseTransferResultTypeCacheDeserializer.class)
public class CriminalCaseTransferResultType extends AbstractEmiDict {
}