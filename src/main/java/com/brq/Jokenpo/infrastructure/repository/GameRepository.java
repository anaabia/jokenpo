package com.brq.Jokenpo.infrastructure.repository;

import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.model.Gamer;
import com.brq.Jokenpo.domain.repository.PersisteRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GameRepository implements PersisteRepository<Game, UUID> {

    private HashMap<UUID, Game> gamers = new HashMap<>();

    public Game addGamer(Gamer gamer){
        Optional<Game> currentGame = findGamerOpen();
        if (currentGame.isPresent()){
            return gamers.put(currentGame.get().getUuid(), currentGame.get().updateGame(gamer));
        } else {
            return save(Game.builder().gamers(Collections.singletonList(gamer)).build());
        }
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
        Optional<Map.Entry<UUID, Game>> currentGame = gamers.entrySet().stream().filter(entry -> Objects.isNull(entry.getValue().getWinner())).findAny();
        return currentGame.map(Map.Entry::getValue);
    }

    @Override
    public Optional<Game> find(UUID id) {
        Optional<Game> currentGame = findGamerOpen();
        if (currentGame.isPresent()){
            Optional<Gamer> gamer = currentGame.get().getGamers().stream().filter(gamer1 -> gamer1.getUuid().equals(id)).findFirst();
           if (gamer.isPresent()){
               return  currentGame;
           }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Game> findName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean delete(UUID uuid) {
        Optional<Game> currentGame = findGamerOpen();
        if (currentGame.isPresent()){
            currentGame.get().removeGame(uuid);
            return currentGame.get().getGamers().stream().noneMatch(gamer1 -> gamer1.getUuid().equals(uuid));
        }
        return false;
    }

    @Override
    public Collection<Game> findAll() {
        return gamers.values();
    }
}
