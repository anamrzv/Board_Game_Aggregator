package application.pojo;

import application.domain.Game;
import application.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponse {
    private List<Game> gamesInStock;
    private List<Shop> shopChain;
}
