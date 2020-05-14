package com.brq.Jokenpo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    private UUID uuid;

    private List<Gamer> gamers;

    private String winner;

    private String history;

    public Game updateGame(Gamer gamer) {
        this.gamers = this.gamers.stream().filter(gamer1 -> !gamer1.getUuid().equals(gamer.getUuid())).collect(Collectors.toList());
        this.getGamers().add(gamer);
        return this;
    }

    public Game removeGame(UUID UUID) {
        this.gamers = this.gamers.stream().filter(gamer1 -> !gamer1.getUuid().equals(UUID)).collect(Collectors.toList());
        return this;
    }
}
