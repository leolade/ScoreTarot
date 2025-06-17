package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.PlayerEntity;
import fr.ladevie.tarot.score.business.RoundEntity;
import fr.ladevie.tarot.score.business.enums.ChelemStatus;
import fr.ladevie.tarot.score.business.enums.ContractType;
import fr.ladevie.tarot.score.business.enums.PetitAuBoutStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoundScoringService {

    public Map<UUID, Integer> calculerScores(RoundEntity round) {
        Map<UUID, Integer> score = new HashMap<>();
        int nbPlayers = round.getGame().getPlayers().size();
        int ecart = round.getScoreAttaque() - seuilPourGagner(round.getNombreBouts());

        boolean contratReussi = ecart >= 0;

        int base = baseScore(round.getContractType());
        int primePetit = primePetitAuBout(round.getPetitStatus());
        int primeChelem = primeChelem(round.getChelemStatus());
        int primesPoignees = 20 * round.getPoigneeSimples().size()
                + 30 * round.getPoigneeDoubles().size()
                + 40 * round.getPoigneeTriple().size();
        int primesMiseres = 10 * round.getMisereSimples().size()
                + 20 * round.getMisereDoubles().size();

        int total = base + Math.abs(ecart) + primePetit + primeChelem + primesPoignees + primesMiseres;
        if (!contratReussi) total = -total;

        int nbDefenseurs = nbPlayers - (round.getCalled() != null ? 2 : 1);
        int coef = round.getCalled() != null ? 2 : 1;

        for (PlayerEntity player : round.getPlayers()) {
            UUID id = player.getId();
            if (id.equals(round.getTaker().getId())) {
                score.put(id, total * coef);
            } else if (round.getCalled() != null && id.equals(round.getCalled().getId())) {
                score.put(id, total);
            } else {
                score.put(id, -total / nbDefenseurs);
            }
        }

        return score;
    }

    private int baseScore(ContractType contractType) {
        return switch (contractType) {
            case PETITE -> 25;
            case GARDE -> 50;
            case GARDE_SANS -> 100;
            case GARDE_CONTRE -> 150;
        };
    }

    private int seuilPourGagner(Integer nombreBouts) {
        return switch (nombreBouts) {
            case 1 -> 51;
            case 2 -> 41;
            case 3 -> 36;
            default -> 56;
        };
    }

    private int primePetitAuBout(PetitAuBoutStatus status) {
        return switch (status) {
            case GAGNE -> 10;
            case PERDU -> -10;
            default -> 0;
        };
    }

    private int primeChelem(ChelemStatus status) {
        return switch (status) {
            case ANNONCE_REUSSI -> 400;
            case ANNONCE_RATE -> -200;
            case REUSSI_NON_ANNONCE -> 200;
            default -> 0;
        };
    }
}
