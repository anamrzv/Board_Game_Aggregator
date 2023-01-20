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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_login")
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "date_of_add")
    LocalDateTime dateOfAdd;

    @Column(name = "date_of_buy")
    LocalDateTime dateOfBuy;

    Integer shop;


    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }
    @Override
    public int hashCode() {
        return 413;
    }
}
