package uz.ciasev.ubdd_service.entity.dict;

import java.util.List;

public enum FileFormatAlias {
    PDF,
    DOC,
    DOCX,
    XLSX,
    PPT,
    ODT,
    XLS,

    MP3,
    M4A,

    AVI,
    MP4,
    GIF,

    SVG,
    JPEG,
    PNG,
    JPG;

    public static List<FileFormatAlias> getImages() {
        return List.of(
                SVG,
                JPEG,
                PNG,
                JPG
        );
    }
}
