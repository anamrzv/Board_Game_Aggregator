package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "board_game", indexes = {@Index(name = "Genre_Index", columnList = "Genre_ID"), @Index(name = "Mechanics_Index", columnList = "Mechanics_ID")})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String image;
    private String video;
    private String description;
    @Column(name = "min_players")
    private Integer minPlayersNumber;
    @Column(name = "max_players")
    private Integer maxPlayersNumber;
    @Column(name = "recommended_players")
    private Integer recPlayersNumber;
    @Column(name = "min_playtime")
    private Integer minPlayTime;
    @Column(name = "max_playtime")
    private Integer maxPlayTime;
    @Column(name = "year_publish")
    private Integer publishYear;
    private String designers;
    @Column(name = "min_age")
    private Integer minPlayAge;
    @Column(name = "max_age")
    private Integer maxPlayAge;
    private Float difficultness;
    private Integer weight;

    @Column(name = "wishlist")
    private Integer wishlistCount;
    @Column(name = "own")
    private Integer ownCount;

    @OneToOne
    @JoinColumn(name = "genre_id")
    private GameGenre genre;
    @OneToOne
    @JoinColumn(name = "mechanics_id")
    private GameMechanics mechanics;

    @OneToOne
    @JoinColumn(name = "publisher")
    private GamePublisher publisher;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "game_to_theme",
            joinColumns = {
                    @JoinColumn(name = "board_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "theme_id", referencedColumnName = "id")
            })
    private Set<GameTheme> themes = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserCart> usersWhoAddedInCart = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserFav> usersWhoLiked = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<GameShop> shopsHavingInStock = new HashSet<>();

    @Override
    public int hashCode() {
        return 31;
    }
}