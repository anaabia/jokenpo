package com.brq.Jokenpo.domain.service;

import com.brq.Jokenpo.domain.dto.GamerDto;
import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.infrastructure.exception.AppException;
import com.brq.Jokenpo.infrastructure.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    protected GameRepository gameRepository;

    @Autowired
    protected PlayerService playerService;

    public Game addGamer(GamerDto gamerDto){
        if (playerService.findPlayer(gamerDto.getUuid()).isPresent()){
            return gameRepository.addGamer(gamerDto.convertToGamer());
        }
        throw new AppException("Jogador não cadastrado");
    }

    public boolean deleteGamer(UUID uuid){
        Optional<Game> gamer = gameRepository.find(uuid);
        if (gamer.isPresent()){
            return gameRepository.delete(uuid);
        }
        throw new AppException("Jogador não cadastrado");
    }

    public Collection<Game> allGames(){
        return gameRepository.findAll();
    }
}
