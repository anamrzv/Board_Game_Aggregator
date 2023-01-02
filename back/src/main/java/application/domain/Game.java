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
@Table(name = "board_game")
public class Game {
    @Id
    @NotNull
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
    @JoinColumn
    private GamePublisher publisher;
    @ManyToMany
    private Set<GameTheme> themes;

}