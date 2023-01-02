package application.service;

import application.domain.Game;
import application.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional
    public Game findById(int gameId) { return gameRepository.findById(gameId).orElse(null); }
}
