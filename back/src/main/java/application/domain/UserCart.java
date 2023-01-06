package application.domain;

import application.domain.composite_keys.UserCartKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts_of_users")
public class UserCart {
    @EmbeddedId
    @JsonIgnore
    UserCartKey id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    Game game;

    @ManyToOne
    @MapsId("userLogin")
    @JoinColumn(name = "user_login")
    @JsonIgnore
    User user;

    @Column(name = "date_of_add")
    LocalDateTime dateOfAdd;

    @Column(name = "date_of_buy")
    LocalDateTime dateOfBuy;

    @Override
    public int hashCode() {
        return 413;
    }
}
