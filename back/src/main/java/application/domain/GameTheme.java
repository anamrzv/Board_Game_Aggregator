package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "game_theme")
public class GameTheme {
    @Id
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "themes", fetch = FetchType.LAZY)
    private Set<Game> games;
}
