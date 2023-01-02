package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "game_mechanics")
public class GameMechanics {
    @Id
    private Integer id;
    private String name;
}
