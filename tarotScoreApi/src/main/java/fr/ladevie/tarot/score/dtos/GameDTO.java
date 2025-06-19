package fr.ladevie.tarot.score.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameDTO {
    private UUID id;
    private LocalDateTime createdDate;
    private List<UserDTO> players = new ArrayList<>();
    private List<RoundWithScoresDTO> rounds = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public GameDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public List<UserDTO> getPlayers() {
        return players;
    }

    public GameDTO setPlayers(List<UserDTO> players) {
        this.players = players;
        return this;
    }

    public List<RoundWithScoresDTO> getRounds() {
        return rounds;
    }

    public GameDTO setRounds(List<RoundWithScoresDTO> rounds) {
        this.rounds = rounds;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public GameDTO setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
