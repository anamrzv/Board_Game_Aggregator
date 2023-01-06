package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public void addForumToFav(Forum forum) {
        this.favouriteForums.add(forum);
        forum.getUsersWhoLiked().add(this);
    }

    public void removeForumFromFav(Forum forum) {
        this.favouriteForums.remove(forum);
        forum.getUsersWhoLiked().remove(this);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserCart> gamesInCart = new HashSet<>();

    // TODO: переписать методы
    /* public void addGameToCart(){

    }
    public void removeGameFromCart(){

    }
     */

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserFav> gamesInFavourites = new HashSet<>();

    // TODO: переписать методы
//    public void addGameToFav(Game game) {
//
//    }
//
//    public void removeGameFromFav(Game game) {
//
//    }

    @Override
    public int hashCode() {
        return 171;
    }

}
