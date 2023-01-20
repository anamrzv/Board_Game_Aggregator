package application.repository;

import application.domain.Game;
import application.domain.User;
import application.domain.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Integer> {

    UserCart getUserCartByUserAndGame(User user, Game game);

    void deleteUserCartByUserAndGame(User user, Game game);
}
