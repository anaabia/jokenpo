package com.brq.Jokenpo.infrastructure.repository;

import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.model.Gamer;
import com.brq.Jokenpo.domain.repository.PersisteRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameRepository implements PersisteRepository<Game, UUID> {

    private HashMap<UUID, Game> gamers = new HashMap<>();

    public Game addGamer(Gamer gamer){
        return findGamerOpen()
                .map(currentGame -> gamers.put(currentGame.getUuid(), currentGame.updateGame(gamer)))
                .orElseGet(() -> save(Game.builder().gamers(Collections.singletonList(gamer)).build()));
    }

    @Override
    public Game save(Game game) {
        if(Objects.nonNull(game.getUuid())){
            gamers.put(game.getUuid(), game);
            return game;
        }
        UUID uuid = UUID.randomUUID();
        Game newGame = Game.builder().gamers(game.getGamers()).winner(game.getWinner()).uuid(uuid).build();
        gamers.put(uuid, newGame);
        return newGame;
    }

    public Optional<Game> findGamerOpen() {
        return gamers
                .entrySet()
                .stream()
                .filter(entry -> Objects.isNull(entry.getValue().getWinner()))
                .findAny()
                .map(Map.Entry::getValue);
    }

    @Override
    public Optional<Game> find(UUID id) {
        return findGamerOpen().flatMap(currentGame -> currentGame
                .getGamers()
                .stream()
                .filter(gamer1 -> gamer1.getUuid().equals(id))
                .findFirst()
                .map(gamer -> currentGame));
    }

    @Override
    public Optional<Game> findName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean delete(UUID uuid) {
        return findGamerOpen()
                .map(currentGame -> {
                    currentGame.removeGame(uuid);
                    return currentGame.getGamers()
                            .stream()
                            .noneMatch(gamer1 -> gamer1.getUuid().equals(uuid));
                })
                .orElse(false);
    }

    @Override
    public Collection<Game> findAll() {
        return gamers.values();
    }
}
