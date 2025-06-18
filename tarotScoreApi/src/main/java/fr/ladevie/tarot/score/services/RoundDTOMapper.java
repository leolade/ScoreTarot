package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.RoundEntity;
import fr.ladevie.tarot.score.dtos.RoundDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RoundDTOMapper {
    private final PlayerBS playerBS;

    public RoundDTOMapper(PlayerBS playerBS) {
        this.playerBS = playerBS;
    }

    public void patchBO(RoundEntity bo, RoundDTO dto) {
        bo.setContractType(dto.getContractType());
        bo.setPetitAuBoutStatus(dto.getPetitStatus());
        bo.setChelemStatus(dto.getChelemStatus());
        bo.setScoreAttaque(dto.getScoreAttaque());
        bo.setPlayers(dto.getPlayers().stream().map(playerBS::getOrCreatePlayer).collect(Collectors.toList()));
        bo.setTaker(playerBS.findPlayerByName(bo.getPlayers(), dto.getTaker().getName()));
        bo.setCalled(dto.getCalled() == null ? null : playerBS.findPlayerByName(bo.getPlayers(), dto.getCalled().getName()));
        bo.setPoigneeSimples(dto.getPoigneeSimples().stream().map(p -> playerBS.findPlayerByName(bo.getPlayers(), p.getName())).collect(Collectors.toList()));
        bo.setPoigneeDoubles(dto.getPoigneeDoubles().stream().map(p -> playerBS.findPlayerByName(bo.getPlayers(), p.getName())).collect(Collectors.toList()));
        bo.setPoigneeTriple(dto.getPoigneeTriple().stream().map(p -> playerBS.findPlayerByName(bo.getPlayers(), p.getName())).collect(Collectors.toList()));
        bo.setMisereSimples(dto.getMisereSimples().stream().map(p -> playerBS.findPlayerByName(bo.getPlayers(), p.getName())).collect(Collectors.toList()));
        bo.setMisereDoubles(dto.getMisereDoubles().stream().map(p -> playerBS.findPlayerByName(bo.getPlayers(), p.getName())).collect(Collectors.toList()));
        bo.setNombreBouts(dto.getNombreBouts());
    }
}
