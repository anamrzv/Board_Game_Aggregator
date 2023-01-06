package application.pojo;

import application.domain.Game;
import application.domain.GameComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private Game game;
    private List<GameComment> comments;
    private List<GameWithStockResponse> prices;
}
