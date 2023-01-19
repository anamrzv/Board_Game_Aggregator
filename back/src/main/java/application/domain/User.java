package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {@Index(name = "User_Role_Index", columnList = "user_role"), @Index(name = "Mailing_Index", columnList = "wants_mailing")})
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

    ////////////////////
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserCart> gamesInCart = new HashSet<>();

    public void addGameToCart(UserCart userCart){
        this.gamesInCart.add(userCart);
        userCart.getGame().getUsersWhoAddedInCart().add(userCart);
    }

    public void removeGameFromCart(UserCart userCart){
        this.gamesInCart.remove(userCart);
        userCart.getGame().getUsersWhoAddedInCart().remove(userCart);
    }

    //////////////////////

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserFav> gamesInFavourites = new HashSet<>();

    public void addGameToFav(UserFav userFav) {
        this.gamesInFavourites.add(userFav);
        userFav.getGame().getUsersWhoLiked().add(userFav);
    }

    public void removeGameFromFav(UserFav userFav) {
        this.gamesInFavourites.remove(userFav);
        userFav.getGame().getUsersWhoLiked().remove(userFav);
    }

    @Override
    public int hashCode() {
        return 171;
    }

}
