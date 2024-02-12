package uz.ciasev.ubdd_service.entity.temporary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ubdd_decision_list202311")
@NoArgsConstructor
@AllArgsConstructor
public class UbddDecison202311 {

    @Id
    private String number;
    private Boolean status;
    private String info;
    @Column(name = "send_date")
    private LocalDateTime sendDate;

}
