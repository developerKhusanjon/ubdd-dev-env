package uz.ciasev.ubdd_service.entity.wanted_vehicle;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vehicle", schema = "wanted_vehicle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private LocalDateTime createdTime;
   private LocalDateTime editedTime;

   private String number;

}
