package uz.ciasev.ubdd_service.entity.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "d_brv")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Brv {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    private Long amount;

    @Getter
    private LocalDate fromDate;

    @Getter
    private LocalDate toDate;

    public Brv(Long amount, LocalDate fromDate) {
        this.amount = amount;
        this.fromDate = fromDate;
    }

    public void close(LocalDate closureDate) {
        this.toDate = closureDate;
    }

    public void update(Long amount) {
        this.amount = amount;
    }
}
