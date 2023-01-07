package application.pojo.response;

import application.domain.GameShop;
import application.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopWithStockResponse {
    private Shop shop;
    private Set<GameShop> gamesWithPrice;
}
