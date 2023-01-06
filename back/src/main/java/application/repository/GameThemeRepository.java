package application.repository;

import application.domain.GameTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameThemeRepository extends JpaRepository<GameTheme, Integer> {
    //List<GameTheme> findGameThemesByGamesId(Integer gameId)
}
