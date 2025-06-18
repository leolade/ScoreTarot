package fr.ladevie.tarot.score.controllers;

import fr.ladevie.tarot.score.dtos.RoundDTO;
import fr.ladevie.tarot.score.services.RoundFacade;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tarot/score/round")
public class RoundController {

    private final RoundFacade roundFacade;

    public RoundController(RoundFacade roundFacade) {
        this.roundFacade = roundFacade;
    }

    @GetMapping("{roundId}")
    public RoundDTO getRound(@PathVariable String roundId) {
        return this.roundFacade.getRound(roundId);
    }

    @DeleteMapping("{roundId}")
    public void deleteRound(@PathVariable String roundId) {
        this.roundFacade.deleteRound(roundId);
    }

    @PutMapping("{roundId}")
    public void editRound(@PathVariable String roundId, @RequestBody RoundDTO roundDTO) {
        this.roundFacade.editRound(roundId, roundDTO);
    }
}
