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
@Table(name = "game_theme")
public class GameTheme {
    @Id
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "themes", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Game)) return false;
        return id != null && id.equals(((GameTheme) o).getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
