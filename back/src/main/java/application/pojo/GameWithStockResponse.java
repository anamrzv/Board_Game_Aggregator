package application.pojo;

import application.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameWithStockResponse {
    private Shop shop;
    private Float price;
}
