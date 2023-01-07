package application.repository;

import application.domain.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameGenreRepository extends JpaRepository<GameGenre, Integer> {
    GameGenre getGameGenreByName(String name);
}
