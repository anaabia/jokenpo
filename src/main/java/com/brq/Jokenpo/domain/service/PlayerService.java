package com.brq.Jokenpo.domain.service;

import com.brq.Jokenpo.domain.dto.PlayerDto;
import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.infrastructure.exception.AppException;
import com.brq.Jokenpo.infrastructure.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    protected PlayerRepository playerRepository;

    public Player createPlayer(PlayerDto playerDto){
        Optional<Player> player = playerRepository.findName(playerDto.getName());
        if (!player.isPresent()){
            return playerRepository.save(playerDto.convertToPlayer());
        }
        throw new AppException("Jogador já cadastrado");
    }

    public Player updatePlayer(UUID uuid, String newName) {
        return playerRepository
                .find(uuid)
                .map(player1 -> playerRepository.save(Player.builder().name(newName).uuid(uuid).build()))
                .orElseThrow(() -> new AppException("Jogador não cadastrado"));
    }

    public boolean deletePlayer(UUID uuid){
        return playerRepository
                .find(uuid)
                .map(game -> playerRepository.delete(uuid))
                .orElseThrow(() -> new AppException("Jogador não cadastrado"));
    }

    public Optional<Player> findPlayer(UUID uuid) {
        return playerRepository.find(uuid);
    }

    public Collection<Player> allPlayers(){
        return playerRepository.findAll();
    }
}
