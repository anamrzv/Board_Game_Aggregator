package application.repository;

import application.domain.GameMechanics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMechanicsRepository extends JpaRepository<GameMechanics, Integer> {
    GameMechanics getGameMechanicsByName(String name);
}
