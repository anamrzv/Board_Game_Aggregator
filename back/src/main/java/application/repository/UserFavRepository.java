package application.repository;

import application.domain.Game;
import application.domain.User;
import application.domain.UserFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavRepository extends JpaRepository<UserFav, Integer> {

    UserFav getUserFavByUserAndGame(User user, Game game);

}
