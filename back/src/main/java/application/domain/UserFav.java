package application.domain;

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
@Table(name = "favorites_of_users")
public class UserFav {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    Game game;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_login")
    User user;

    @Column(name = "date_of_add")
    LocalDateTime dateOfAdd;

    @Override
    public int hashCode() {
        return 415;
    }
}
