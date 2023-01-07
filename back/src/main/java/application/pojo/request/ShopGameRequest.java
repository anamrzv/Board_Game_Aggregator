package application.pojo.request;

import lombok.Data;

@Data
public class ShopGameRequest {
    Integer shopId;
    Integer gameId;
    Float price;
}
