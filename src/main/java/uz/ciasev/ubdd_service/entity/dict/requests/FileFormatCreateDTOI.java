package uz.ciasev.ubdd_service.entity.dict.requests;

public interface FileFormatCreateDTOI extends DictCreateDTOI, FileFormatUpdateDTOI {
    String getExtension();
}
