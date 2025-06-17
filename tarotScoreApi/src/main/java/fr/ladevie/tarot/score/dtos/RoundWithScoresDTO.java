package fr.ladevie.tarot.score.dtos;

import fr.ladevie.tarot.score.business.RoundEntity;

import java.util.Map;
import java.util.UUID;

public class RoundWithScoresDTO extends RoundDTO {
    private Map<UUID, Integer> scores;

    public RoundWithScoresDTO() {
    }

    public RoundWithScoresDTO(RoundEntity entity) {
        super(entity);
    }

    public Map<UUID, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<UUID, Integer> scores) {
        this.scores = scores;
    }
}
