package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.*;
import fr.ladevie.tarot.score.dtos.GameDTO;
import fr.ladevie.tarot.score.dtos.RoundDTO;
import fr.ladevie.tarot.score.dtos.UserDTO;
import fr.ladevie.tarot.score.exceptions.GameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service()
public class GameFacade {

    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final RoundWithScoresDTOMapper roundWithScoresDTOMapper;
    private final RoundDTOMapper roundDTOMapper;

    public GameFacade(GameRepository gameRepository, RoundRepository roundRepository, RoundWithScoresDTOMapper roundWithScoresDTOMapper, RoundDTOMapper roundDTOMapper) {
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.roundWithScoresDTOMapper = roundWithScoresDTOMapper;
        this.roundDTOMapper = roundDTOMapper;
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void addRound(String id, RoundDTO roundDTO) {
        GameEntity game = this.gameRepository.findById(UUID.fromString(id)).orElseThrow(GameNotFoundException::new);
        RoundEntity round = new RoundEntity();
        round.setGame(game);
        roundDTOMapper.patchBO(round, roundDTO);
        this.roundRepository.save(round);
    }

    public GameDTO getGame(String id) {
        GameEntity game = this.gameRepository.findById(UUID.fromString(id)).orElseThrow(GameNotFoundException::new);
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setRounds(
                game
                        .getRounds()
                        .stream()
                        .map(roundWithScoresDTOMapper::toDto)
                        .collect(Collectors.toList()));
        gameDTO.setPlayers(game.getRounds()
                .stream()
                .map(RoundEntity::getPlayers)
                .flatMap(Collection::stream)
                .filter(distinctByKey(PlayerEntity::getId))
                .map(UserDTO::new)
                .collect(Collectors.toList())
        );
        return gameDTO;
    }

    public UUID newGame() {
        GameEntity game = new GameEntity();
        game.setCreatedAt(LocalDateTime.now());
        game.setRounds(new ArrayList<>());

        game = this.gameRepository.save(game);
        return game.getId();
    }
}
