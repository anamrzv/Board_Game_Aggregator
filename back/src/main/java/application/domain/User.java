package application.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull
    @Column(name = "login")
    private String username;
    @NotNull
    @Column(name = "hashed_password")
    private String password;
    @Column(name = "salt")
    private String salt;
    private String mail;
    @Column(name = "wants_mailing")
    private Boolean wantsMailing;
    @Column(name = "user_role")
    private Integer userRole;

}
