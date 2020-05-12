package com.brq.Jokenpo.application.controller;

import com.brq.Jokenpo.domain.dto.PlayerDto;
import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    protected PlayerService playerService;

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDto player) {
        return ResponseEntity.ok(playerService.createPlayer(player));
    }

    @PutMapping({"/{uuid}"})
    public ResponseEntity<Player> updatePlayer(@PathVariable(name = "uuid") UUID uuid, @RequestBody PlayerDto playerDto) {
        return ResponseEntity.ok(playerService.updatePlayer(uuid, playerDto.getName()));
    }

    @DeleteMapping({"/{uuid}"})
    public ResponseEntity deletePlayer(@PathVariable(name = "uuid") UUID uuid) {
        return ResponseEntity.ok(playerService.deletePlayer(uuid));
    }

    @GetMapping()
    public ResponseEntity players() {
        return ResponseEntity.ok(playerService.allPlayers());
    }
}
