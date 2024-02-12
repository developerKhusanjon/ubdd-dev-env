package uz.ciasev.ubdd_service.entity.dict.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.UserStatusDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.UserStatusDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "user_status")
@NoArgsConstructor
@JsonDeserialize(using = UserStatusDeserializer.class)
public class UserStatus extends AbstractEmiDict {

      @Getter
      private Boolean isUserActive;

      @Getter
      private String color;

      public void construct(UserStatusDTOI request) {
            super.construct(request);
            set(request);
      }

      public void update(UserStatusDTOI request) {
            super.update(request);
            set(request);
      }

      private void set(UserStatusDTOI request) {
            this.isUserActive = request.getIsUserActive();
            this.color = request.getColor();
      }
}
