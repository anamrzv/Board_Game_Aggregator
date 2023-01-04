package application.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {@Index(name = "User_Role_Index", columnList = "user_role")})
public class User {
    @Id
    @Column(name = "login")
    private String username;
    @NotNull
    @Column(name = "hashed_password")
    private String password;
    private String salt;
    private String mail;
    @Column(name = "wants_mailing")
    private Boolean wantsMailing;
    @OneToOne
    @JoinColumn(name = "user_role")
    private UserRole userRole;
    private String shop;

    @ManyToMany
    private Set<Forum> favouriteForums;
    @ManyToMany
    private Set<Game> gamesInCart; //TODO: сделать добавление
    @ManyToMany
    private Set<Game> gamesInFavourites;

}
