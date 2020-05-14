package com.brq.Jokenpo.infrastructure.repository;

import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.domain.repository.PersisteRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PlayerRepository implements PersisteRepository<Player, UUID> {

    private HashMap<UUID, String> players = new HashMap<>();

    @Override
    public Player save(Player player) {
        if(Objects.nonNull(player.getUuid())){
            players.put(player.getUuid(), player.getName());
            return player;
        }
        UUID uuid = UUID.randomUUID();
        players.put(uuid, player.getName());
        return Player.builder().name(player.getName()).uuid(uuid).build();
    }

    @Override
    public Optional<Player> find(UUID id) {
        if(players.containsKey(id)){
            return Optional.of(Player.builder().uuid(id).name(players.get(id)).build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Player> findName(String name) {
        return players
                .entrySet()
                .stream()
                .filter(player -> player.getValue().equalsIgnoreCase(name))
                .findFirst()
                .map(entry -> Player.builder().name(entry.getValue()).uuid(entry.getKey()).build());
    }

    @Override
    public boolean delete(UUID uuid) {
        return !players.remove(uuid).isEmpty();
    }

    @Override
    public Collection<Player> findAll() {
        return players
                .entrySet()
                .stream()
                .map(entry -> Player.builder().name(entry.getValue()).uuid(entry.getKey()).build())
                .collect(Collectors.toList());
    }
}
