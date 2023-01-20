package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts_of_users")
public class UserCart {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "date_of_add")
    LocalDateTime dateOfAdd;

    @Column(name = "date_of_buy")
    LocalDateTime dateOfBuy;

    Integer shop;

    @Override
    public int hashCode() {
        return 413;
    }
}
