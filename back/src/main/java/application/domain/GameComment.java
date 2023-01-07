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
    @Column(name = "game_id")
    private Integer gameId;
    @Column(name = "user_login")
    private String userId;
    private LocalDateTime date;
    private String content;
    public GameComment(Integer gameId, String userId, LocalDateTime date, String content) {
        this.gameId = gameId;
        this.userId = userId;
        this.date = date;
        this.content = content;
    }
}
