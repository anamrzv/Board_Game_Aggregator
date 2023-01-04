package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "game_comment")
public class GameComment {
    @Id
    private Integer id;
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game gameId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private LocalDateTime date;
    private String content;
}
