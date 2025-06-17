package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.RoundEntity;
import fr.ladevie.tarot.score.dtos.RoundWithScoresDTO;
import org.springframework.stereotype.Service;

@Service
public class RoundWithScoresDTOMapper {

    private final RoundScoringService roundScoringService;

    public RoundWithScoresDTOMapper(RoundScoringService roundScoringService) {
        this.roundScoringService = roundScoringService;
    }

    public RoundWithScoresDTO toDto(RoundEntity entity) {
        RoundWithScoresDTO dto = new RoundWithScoresDTO(entity);
        dto.setScores(this.roundScoringService.calculerScores(entity));
        return dto;
    }
}
