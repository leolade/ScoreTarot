package fr.ladevie.tarot.score.business;

import fr.ladevie.tarot.score.business.enums.ChelemStatus;
import fr.ladevie.tarot.score.business.enums.ContractType;
import fr.ladevie.tarot.score.business.enums.PetitAuBoutStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class RoundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    private PetitAuBoutStatus petitAuBoutStatus;

    @Enumerated(EnumType.STRING)
    private ChelemStatus chelemStatus;

    private Integer scoreAttaque;

    private Integer nombreBouts;

    private LocalDateTime createdDate;

    // Relations clés déplacées ici 👇
    @ManyToOne
    @JoinColumn(name = "taker_id", nullable = false)
    private PlayerEntity taker;

    @ManyToOne
    @JoinColumn(name = "called_id")
    private PlayerEntity called;

    // Chaque round appartient à une partie
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    // ✳️ Poignées
    @ManyToMany
    @JoinTable(name = "round_poignee_simple",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> poigneeSimples = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "round_poignee_double",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> poigneeDoubles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "round_poignee_triple",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> poigneeTriple = new ArrayList<>();

    // ✳️ Miséres
    @ManyToMany
    @JoinTable(name = "round_misere_simple",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> misereSimples = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "round_misere_double",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> misereDoubles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "round_players",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerEntity> players = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public PetitAuBoutStatus getPetitAuBoutStatus() {
        return petitAuBoutStatus;
    }

    public void setPetitAuBoutStatus(PetitAuBoutStatus petitStatus) {
        this.petitAuBoutStatus = petitStatus;
    }

    public ChelemStatus getChelemStatus() {
        return chelemStatus;
    }

    public void setChelemStatus(ChelemStatus chelemStatus) {
        this.chelemStatus = chelemStatus;
    }

    public Integer getScoreAttaque() {
        return scoreAttaque;
    }

    public void setScoreAttaque(Integer scoreAttaque) {
        this.scoreAttaque = scoreAttaque;
    }

    public PlayerEntity getTaker() {
        return taker;
    }

    public void setTaker(PlayerEntity taker) {
        this.taker = taker;
    }

    public PlayerEntity getCalled() {
        return called;
    }

    public void setCalled(PlayerEntity called) {
        this.called = called;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public List<PlayerEntity> getPoigneeSimples() {
        return poigneeSimples;
    }

    public void setPoigneeSimples(List<PlayerEntity> poigneeSimples) {
        this.poigneeSimples = poigneeSimples;
    }

    public List<PlayerEntity> getPoigneeDoubles() {
        return poigneeDoubles;
    }

    public void setPoigneeDoubles(List<PlayerEntity> poigneeDoubles) {
        this.poigneeDoubles = poigneeDoubles;
    }

    public List<PlayerEntity> getPoigneeTriple() {
        return poigneeTriple;
    }

    public void setPoigneeTriple(List<PlayerEntity> poigneeTriple) {
        this.poigneeTriple = poigneeTriple;
    }

    public List<PlayerEntity> getMisereSimples() {
        return misereSimples;
    }

    public void setMisereSimples(List<PlayerEntity> misereSimples) {
        this.misereSimples = misereSimples;
    }

    public List<PlayerEntity> getMisereDoubles() {
        return misereDoubles;
    }

    public void setMisereDoubles(List<PlayerEntity> misereDoubles) {
        this.misereDoubles = misereDoubles;
    }

    public Integer getNombreBouts() {
        return nombreBouts;
    }

    public void setNombreBouts(Integer nombreBouts) {
        this.nombreBouts = nombreBouts;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public RoundEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
