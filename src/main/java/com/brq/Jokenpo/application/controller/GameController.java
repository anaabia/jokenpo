package com.brq.Jokenpo.application.controller;

import com.brq.Jokenpo.domain.dto.GamerDto;
import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    protected GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GamerDto gamer) {
        return ResponseEntity.ok(gameService.addGamer(gamer));
    }

    @DeleteMapping({"/{uuid}"})
    public ResponseEntity<Boolean> deleteGame(@PathVariable(name = "uuid") UUID uuid) {
        return ResponseEntity.ok(gameService.deleteGamer(uuid));
    }

    @GetMapping()
    public ResponseEntity<Collection<Game>> Games() {
        return ResponseEntity.ok(gameService.allGames());
    }

    @GetMapping("/winner")
    public ResponseEntity<Game> gameWinner() {
        return ResponseEntity.ok(gameService.finishGame());
    }
}
