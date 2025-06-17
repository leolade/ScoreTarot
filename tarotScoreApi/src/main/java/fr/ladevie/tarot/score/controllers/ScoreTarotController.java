package fr.ladevie.tarot.score.controllers;

import fr.ladevie.tarot.score.business.*;
import fr.ladevie.tarot.score.dtos.GameDTO;
import fr.ladevie.tarot.score.dtos.RoundDTO;
import fr.ladevie.tarot.score.dtos.RoundWithScoresDTO;
import fr.ladevie.tarot.score.dtos.UserDTO;
import fr.ladevie.tarot.score.exceptions.GameNotFoundException;
import fr.ladevie.tarot.score.exceptions.RoundNotFoundException;
import fr.ladevie.tarot.score.services.RoundWithScoresDTOMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/tarot/score")
public class ScoreTarotController {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    private final RoundWithScoresDTOMapper roundWithScoresDTOMapper;

    public ScoreTarotController(GameRepository gameRepository, PlayerRepository playerRepository, RoundRepository roundRepository, RoundWithScoresDTOMapper roundWithScoresDTOMapper) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.roundRepository = roundRepository;
        this.roundWithScoresDTOMapper = roundWithScoresDTOMapper;
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @PostMapping("game")
    public UUID newGame() {
        GameEntity game = new GameEntity();
        game.setCreatedAt(LocalDateTime.now());
        game.setRounds(new ArrayList<>());

        game = this.gameRepository.save(game);
        return game.getId();
    }

    @GetMapping("game/{id}")
    public GameDTO getGame(@PathVariable String id) {
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

    @PostMapping("game/{id}/round")
    public void addRound(@PathVariable String id, @RequestBody RoundDTO roundDTO) {
        GameEntity game = this.gameRepository.findById(UUID.fromString(id)).orElseThrow(GameNotFoundException::new);
        RoundEntity round = new RoundEntity();
        round.setGame(game);
        fillRoundWithDTO(roundDTO, round);
        this.roundRepository.save(round);
    }

    @PutMapping("round/{roundId}")
    public void editRound(@PathVariable String roundId, @RequestBody RoundDTO roundDTO) {
        RoundEntity round = this.roundRepository.findById(UUID.fromString(roundId)).orElseThrow(RoundNotFoundException::new);
        fillRoundWithDTO(roundDTO, round);
        this.roundRepository.save(round);
    }

    public PlayerEntity getOrCreatePlayer(@Nullable UserDTO dto) {
        if (dto == null) {
            return null;
        }
        Optional<PlayerEntity> byId = dto.getId() == null ? Optional.empty() : this.playerRepository.findById(dto.getId());
        if (byId.isPresent()) {
            return byId.get();
        } else {
            PlayerEntity entity = new PlayerEntity();
            entity.setName(dto.getName());
            return this.playerRepository.save(entity);
        }
    }

    @Nullable
    private PlayerEntity findPlayerByName(List<PlayerEntity> players, String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    private void fillRoundWithDTO(RoundDTO roundDTO, RoundEntity round) {
        round.setContractType(roundDTO.getContractType());
        round.setPetitAuBoutStatus(roundDTO.getPetitStatus());
        round.setChelemStatus(roundDTO.getChelemStatus());
        round.setScoreAttaque(roundDTO.getScoreAttaque());
        round.setPlayers(roundDTO.getPlayers().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setTaker(this.findPlayerByName(round.getPlayers(), roundDTO.getTaker().getName()));
        round.setCalled(roundDTO.getCalled() == null ? null : this.findPlayerByName(round.getPlayers(), roundDTO.getCalled().getName()));
        round.setPoigneeSimples(roundDTO.getPoigneeSimples().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setPoigneeDoubles(roundDTO.getPoigneeDoubles().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setPoigneeTriple(roundDTO.getPoigneeTriple().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setMisereSimples(roundDTO.getMisereSimples().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setMisereDoubles(roundDTO.getMisereDoubles().stream().map(this::getOrCreatePlayer).collect(Collectors.toList()));
        round.setNombreBouts(roundDTO.getNombreBouts());
    }
}
