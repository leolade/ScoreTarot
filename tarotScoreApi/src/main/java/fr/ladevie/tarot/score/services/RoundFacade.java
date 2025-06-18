package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.*;
import fr.ladevie.tarot.score.dtos.GameDTO;
import fr.ladevie.tarot.score.dtos.RoundDTO;
import fr.ladevie.tarot.score.dtos.UserDTO;
import fr.ladevie.tarot.score.exceptions.GameNotFoundException;
import fr.ladevie.tarot.score.exceptions.RoundNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service()
public class RoundFacade {

    private final RoundRepository roundRepository;
    private final RoundDTOMapper roundDTOMapper;

    public RoundFacade(PlayerRepository playerRepository, RoundRepository roundRepository, RoundDTOMapper roundDTOMapper) {
        this.roundDTOMapper = roundDTOMapper;
        this.roundRepository = roundRepository;
    }

    public void editRound(String roundId, RoundDTO roundDTO) {
        RoundEntity round = this.roundRepository.findById(UUID.fromString(roundId)).orElseThrow(RoundNotFoundException::new);
        roundDTOMapper.patchBO(round, roundDTO);
        this.roundRepository.save(round);
    }

    public RoundDTO getRound(String roundId) {
        return new RoundDTO(this.roundRepository.findById(UUID.fromString(roundId)).orElseThrow(RoundNotFoundException::new));
    }

    public void deleteRound(String roundId) {
        this.roundRepository.deleteById(UUID.fromString(roundId));
    }
}
