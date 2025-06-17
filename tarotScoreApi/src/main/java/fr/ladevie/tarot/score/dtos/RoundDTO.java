package fr.ladevie.tarot.score.dtos;

import fr.ladevie.tarot.score.business.RoundEntity;
import fr.ladevie.tarot.score.business.enums.ChelemStatus;
import fr.ladevie.tarot.score.business.enums.ContractType;
import fr.ladevie.tarot.score.business.enums.PetitAuBoutStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoundDTO {

    private UUID id;

    private ContractType contractType;
    private PetitAuBoutStatus petitStatus;
    private ChelemStatus chelemStatus;

    private Integer scoreAttaque;
    private Integer nombreBouts;

    private UserDTO taker;
    private UserDTO called;
    private UUID gameId;

    private List<UserDTO> players = new ArrayList<>();
    ;

    private List<UserDTO> poigneeSimples = new ArrayList<>();
    private List<UserDTO> poigneeDoubles = new ArrayList<>();
    private List<UserDTO> poigneeTriple = new ArrayList<>();
    private List<UserDTO> misereSimples = new ArrayList<>();
    private List<UserDTO> misereDoubles = new ArrayList<>();

    public RoundDTO() {
    }

    public RoundDTO(RoundEntity entity) {
        this.id = entity.getId();
        this.contractType = entity.getContractType();
        this.petitStatus = entity.getPetitAuBoutStatus();
        this.chelemStatus = entity.getChelemStatus();
        this.scoreAttaque = entity.getScoreAttaque();
        this.taker = new UserDTO(entity.getTaker());
        this.called = entity.getCalled() == null ? null : new UserDTO(entity.getCalled());
        this.gameId = entity.getGame().getId();
        this.players = entity.getPlayers().stream().map(UserDTO::new).collect(Collectors.toList());
        this.poigneeSimples = entity.getPoigneeSimples().stream().map(UserDTO::new).collect(Collectors.toList());
        this.poigneeDoubles = entity.getPoigneeDoubles().stream().map(UserDTO::new).collect(Collectors.toList());
        this.poigneeTriple = entity.getPoigneeTriple().stream().map(UserDTO::new).collect(Collectors.toList());
        this.misereSimples = entity.getMisereSimples().stream().map(UserDTO::new).collect(Collectors.toList());
        this.misereDoubles = entity.getMisereDoubles().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public RoundDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public RoundDTO setContractType(ContractType contractType) {
        this.contractType = contractType;
        return this;
    }

    public PetitAuBoutStatus getPetitStatus() {
        return petitStatus;
    }

    public RoundDTO setPetitStatus(PetitAuBoutStatus petitStatus) {
        this.petitStatus = petitStatus;
        return this;
    }

    public ChelemStatus getChelemStatus() {
        return chelemStatus;
    }

    public RoundDTO setChelemStatus(ChelemStatus chelemStatus) {
        this.chelemStatus = chelemStatus;
        return this;
    }

    public Integer getScoreAttaque() {
        return scoreAttaque;
    }

    public RoundDTO setScoreAttaque(Integer scoreAttaque) {
        this.scoreAttaque = scoreAttaque;
        return this;
    }

    public UserDTO getTaker() {
        return taker;
    }

    public RoundDTO setTaker(UserDTO taker) {
        this.taker = taker;
        return this;
    }

    public UserDTO getCalled() {
        return called;
    }

    public RoundDTO setCalled(UserDTO called) {
        this.called = called;
        return this;
    }

    public UUID getGameId() {
        return gameId;
    }

    public RoundDTO setGameId(UUID gameId) {
        this.gameId = gameId;
        return this;
    }

    public List<UserDTO> getPlayers() {
        return players;
    }

    public RoundDTO setPlayers(List<UserDTO> players) {
        this.players = players;
        return this;
    }

    public List<UserDTO> getPoigneeSimples() {
        return poigneeSimples;
    }

    public RoundDTO setPoigneeSimples(List<UserDTO> poigneeSimples) {
        this.poigneeSimples = poigneeSimples;
        return this;
    }

    public List<UserDTO> getPoigneeDoubles() {
        return poigneeDoubles;
    }

    public RoundDTO setPoigneeDoubles(List<UserDTO> poigneeDoubles) {
        this.poigneeDoubles = poigneeDoubles;
        return this;
    }

    public List<UserDTO> getPoigneeTriple() {
        return poigneeTriple;
    }

    public RoundDTO setPoigneeTriple(List<UserDTO> poigneeTriple) {
        this.poigneeTriple = poigneeTriple;
        return this;
    }

    public List<UserDTO> getMisereSimples() {
        return misereSimples;
    }

    public RoundDTO setMisereSimples(List<UserDTO> misereSimples) {
        this.misereSimples = misereSimples;
        return this;
    }

    public List<UserDTO> getMisereDoubles() {
        return misereDoubles;
    }

    public RoundDTO setMisereDoubles(List<UserDTO> misereDoubles) {
        this.misereDoubles = misereDoubles;
        return this;
    }

    public Integer getNombreBouts() {
        return this.nombreBouts;
    }

    public RoundDTO setNombreBouts(Integer nombreBouts) {
        this.nombreBouts = nombreBouts;
        return this;
    }
}
