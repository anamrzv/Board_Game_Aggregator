package application.pojo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String login;
    private String password;
    private String mail;
    private Boolean mailPreferences;
}
