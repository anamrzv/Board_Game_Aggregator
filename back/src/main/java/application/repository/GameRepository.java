package application.repository;

import application.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game> {
    //List<Game> findGamesByThemesId(Integer themeId);
}
