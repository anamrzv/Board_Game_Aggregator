package application.pojo.request;

import lombok.Data;

@Data
public class CartRequest {
    String login;
    Integer shopId;
    Integer gameId;
}
