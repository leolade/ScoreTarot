package fr.ladevie.tarot.score.controllers;

import fr.ladevie.tarot.score.dtos.GameDTO;
import fr.ladevie.tarot.score.dtos.RoundDTO;
import fr.ladevie.tarot.score.services.GameFacade;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/tarot/score/game")
public class GameController {

    private final GameFacade gameFacade;

    public GameController(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    @PostMapping("")
    public UUID newGame() {
        return this.gameFacade.newGame();
    }

    @GetMapping("{id}")
    public GameDTO getGame(@PathVariable String id) {
        return this.gameFacade.getGame(id);
    }

    @DeleteMapping("{id}")
    public void deleteGame(@PathVariable String id) {
        this.gameFacade.deleteGame(id);
    }

    @PostMapping("{id}/round")
    public void addRound(@PathVariable String id, @RequestBody RoundDTO roundDTO) {
        this.gameFacade.addRound(id, roundDTO);
    }
}
