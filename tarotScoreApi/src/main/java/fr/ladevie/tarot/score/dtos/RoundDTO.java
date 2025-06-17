package fr.ladevie.tarot.score.dtos;

import fr.ladevie.tarot.score.business.enums.ChelemStatus;
import fr.ladevie.tarot.score.business.enums.ContractType;
import fr.ladevie.tarot.score.business.enums.PetitAuBoutStatus;

import java.util.List;
import java.util.UUID;

public class RoundDTO {

    private UUID id;

    private ContractType contractType;
    private PetitAuBoutStatus petitStatus;
    private ChelemStatus chelemStatus;

    private Integer scoreAttaque;

    private UserDTO taker;
    private UserDTO called;
    private UUID gameId;

    private List<UserDTO> players;

    private List<UserDTO> poigneeSimples;
    private List<UserDTO> poigneeDoubles;
    private List<UserDTO> poigneeTriple;
    private List<UserDTO> misereSimples;
    private List<UserDTO> misereDoubles;
}