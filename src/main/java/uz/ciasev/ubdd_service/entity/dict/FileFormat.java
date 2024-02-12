package uz.ciasev.ubdd_service.entity.dict;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.requests.FileFormatCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.FileFormatUpdateDTOI;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_file_format")
@NoArgsConstructor
public class FileFormat extends AbstractEmiDict {

    @Getter
    private String extension;

    // in kB
    @Getter
    private Long maxSize;

    public Long getMaxSizeInKB() {
        return maxSize;
    }

    public Long getMaxSizeInByte() {
        return maxSize * 1024;
    }

    public void construct(FileFormatCreateDTOI request) {
        super.construct(request);
        set(request);
        this.extension = request.getExtension().toUpperCase();
    }

    public void update(FileFormatUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(FileFormatUpdateDTOI request) {
        this.maxSize = request.getMaxSize();
    }
}
