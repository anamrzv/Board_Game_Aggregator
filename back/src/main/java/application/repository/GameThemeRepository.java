package application.repository;

import application.domain.GameTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameThemeRepository extends JpaRepository<GameTheme, Integer> {
    GameTheme getGameThemeByName(String name);
}
