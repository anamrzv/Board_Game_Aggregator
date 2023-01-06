package application.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_fav_forums",
            joinColumns = {
                    @JoinColumn(name = "user_login", referencedColumnName = "login")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "forum_id", referencedColumnName = "id")
            })
    private Set<Forum> favouriteForums = new HashSet<>();

    public void addForumToFav(Forum forum){
        this.favouriteForums.add(forum);
        forum.getUsersWhoLiked().add(this);
    }
    public void removeForumFromFav(Forum forum){
        this.favouriteForums.remove(forum);
        forum.getUsersWhoLiked().remove(this);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "carts_of_users",
            joinColumns = {
                    @JoinColumn(name = "user_login", referencedColumnName = "login")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "game_id", referencedColumnName = "id")
            })
    private Set<Game> gamesInCart = new HashSet<>();

    public void addGameToCart(Game game){
        this.gamesInCart.add(game);
        game.getUsersWhoAddedInCart().add(this);
    }
    public void removeGameFromCart(Game game){
        this.gamesInCart.remove(game);
        game.getUsersWhoAddedInCart().remove(this);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites_of_users",
            joinColumns = {
                    @JoinColumn(name = "user_login", referencedColumnName = "login")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "game_id", referencedColumnName = "id")
            })
    private Set<Game> gamesInFavourites = new HashSet<>();

    public void addGameToFav(Game game){
        this.gamesInFavourites.add(game);
        game.getUsersWhoLiked().add(this);
    }
    public void removeGameFromFav(Game game){
        this.gamesInFavourites.remove(game);
        game.getUsersWhoLiked().remove(this);
    }

    @Override
    public int hashCode() {
        return 171;
    }

}
