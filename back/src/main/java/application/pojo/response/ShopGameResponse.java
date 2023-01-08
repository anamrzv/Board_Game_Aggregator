package application.pojo.response;

import application.domain.Game;
import application.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopGameResponse {
    Shop shop;
    Game game;
    Float price;
}
