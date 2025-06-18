package fr.ladevie.tarot.score.services;

import fr.ladevie.tarot.score.business.PlayerEntity;
import fr.ladevie.tarot.score.business.RoundEntity;
import fr.ladevie.tarot.score.business.enums.ChelemStatus;
import fr.ladevie.tarot.score.business.enums.ContractType;
import fr.ladevie.tarot.score.business.enums.PetitAuBoutStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RoundScoringService {

    int BASE_POINT = 25;
    int PETIT_AU_BOUT = 10;
    int VALEUR_POIGNEE = 20;
    int VALEUR_DOUBLE_POIGNEE = 30;
    int VALEUR_TRIPLE_POIGNEE = 40;

    public Map<UUID, Integer> calculerScores(RoundEntity round) {
        Map<UUID, Integer> scores = new HashMap<>();
        int contractMultiplier = contractMultiplier(round.getContractType());
        int ecart = Math.abs(round.getScoreAttaque() - seuilPourGagner(round.getNombreBouts()));

        int nbDefenseurs = round.getPlayers().size() - (round.getCalled() == null ? 1 : 2);
        int nbAttaquant = round.getPlayers().size() - nbDefenseurs;

        int scorePourUnAttaquant = getScorePourUnAttaquant(round, ecart, contractMultiplier);

        for (PlayerEntity player : round.getPlayers()) {
            int malusMisere = this.getMalusMisere(player, round);
            int bonusMisere = this.getBonusMisere(player, round);

            int total;
            if (this.isTaker(player, round)) {
                total = (scorePourUnAttaquant * ((nbDefenseurs - nbAttaquant) + 1)) + bonusMisere - malusMisere;
            } else if (this.isCalled(player, round)) {
                total = scorePourUnAttaquant + bonusMisere - malusMisere;
            } else {
                total = -scorePourUnAttaquant + bonusMisere - malusMisere;
            }
            scores.put(player.getId(), total);
        }

        return scores;
    }

    private int getScorePourUnAttaquant(RoundEntity round, int ecart, int contractMultiplier) {
        boolean victoireAttaque = (round.getScoreAttaque() >= seuilPourGagner(round.getNombreBouts())) && !ChelemStatus.ANNONCE_RATE.equals(round.getChelemStatus());

        int scoreTotalPourUn = (
                ((BASE_POINT + ecart) * contractMultiplier)
                        + (round.getPoigneeSimples().size() * VALEUR_POIGNEE)
                        + (round.getPoigneeDoubles().size() * VALEUR_DOUBLE_POIGNEE)
                        + (round.getPoigneeTriple().size() * VALEUR_TRIPLE_POIGNEE)
        );

        return (victoireAttaque ? scoreTotalPourUn : -scoreTotalPourUn) + this.getBonusPetitAuBout(round) + this.primeChelem(round.getChelemStatus());
    }

    private int getBonusPetitAuBout(RoundEntity round) {
        return switch (round.getPetitAuBoutStatus()) {
            case NON -> 0;
            case GAGNE -> 10;
            case PERDU -> -10;
        };
    }

    private int getBonusMisere(PlayerEntity player, RoundEntity round) {
        return round.getMisereSimples()
                .stream()
                .filter(p -> p.getId().equals(player.getId()))
                .map(p -> 10)
                .reduce(0, Integer::sum) +
                round.getMisereDoubles()
                        .stream()
                        .filter(p -> p.getId().equals(player.getId()))
                        .map(p -> 20)
                        .reduce(0, Integer::sum);
    }

    private int getMalusMisere(PlayerEntity player, RoundEntity round) {
        return round.getMisereSimples()
                .stream()
                .filter(p -> !p.getId().equals(player.getId()))
                .map(p -> 10)
                .reduce(0, Integer::sum) +
                round.getMisereDoubles()
                        .stream()
                        .filter(p -> !p.getId().equals(player.getId()))
                        .map(p -> 20)
                        .reduce(0, Integer::sum);
    }

    private boolean isCalled(PlayerEntity player, RoundEntity round) {
        if (round.getCalled() == null) {
            return false;
        }
        return player.getId().equals(round.getCalled().getId());
    }

    private boolean isTaker(PlayerEntity player, RoundEntity round) {
        return player.getId().equals(round.getTaker().getId());
    }

    private int contractMultiplier(ContractType contractType) {
        return switch (contractType) {
            case PETITE -> 1;
            case GARDE -> 2;
            case GARDE_SANS -> 4;
            case GARDE_CONTRE -> 6;
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
