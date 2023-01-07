package application.repository;

import application.domain.GamePublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePublisherRepository extends JpaRepository<GamePublisher, Integer> {
    GamePublisher getGamePublisherByName(String name);
}
