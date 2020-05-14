package com.brq.Jokenpo.domain.service;

import com.brq.Jokenpo.domain.dto.GamerDto;
import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.domain.enums.WinnerPlayedType;
import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.model.Gamer;
import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.infrastructure.exception.AppException;
import com.brq.Jokenpo.infrastructure.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    protected GameRepository gameRepository;

    @Autowired
    protected PlayerService playerService;

    public Game addGamer(GamerDto gamerDto){
        return playerService.findPlayer(gamerDto.getUuid())
                .map(game -> gameRepository.addGamer(gamerDto.convertToGamer()))
                .orElseThrow(() -> new AppException("Jogador não cadastrado"));
    }

    public boolean deleteGamer(UUID uuid){
        Optional<Game> gamer = gameRepository.find(uuid);
        return gamer.
                map(game -> gameRepository.delete(uuid))
                .orElseThrow(() -> new AppException("Jogador não cadastrado"));
    }

    public Collection<Game> allGames(){
        return gameRepository.findAll();
    }

    public Game finishGame() {
        return gameRepository.findGamerOpen().map( game -> {
            List<Gamer> gamers = game.getGamers();
            StringBuilder history = new StringBuilder();
            //Iterate the players and sort randomly.
            final List<Gamer> lives = gamers.stream()
                    .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
                    .collect(Collectors.toList());
            AtomicReference<List<Gamer>> lostPlayers = new AtomicReference<>();

            // Search list until find a winner ou the winners
            while (lives.stream().collect(Collectors.groupingBy(Gamer::getPlayedType)).size() != 1){
                lostPlayers.set(new ArrayList<>());
                WinnerPlayedType winnerPlayedType = null;
                for (Gamer gamer : lives){
                    winnerPlayedType = WinnerPlayedType.getWeakType(gamer.getPlayedType());

                    // Search the players who must be deleted.
                    lostPlayers.set(lives.stream()
                            .filter(gamer1 -> WinnerPlayedType.getWeakType(gamer.getPlayedType()).getLostType().contains(gamer1.getPlayedType()))
                            .collect(Collectors.toList()));
                    if (lostPlayers.get().size() > 0) {
                        break;
                    }
                }
                // Delete the player who must be remove of the survivors list.
                if(lostPlayers.get().size() < lives.size()) {
                    saveHistory(history, lostPlayers, winnerPlayedType);
                    lives.removeAll(lostPlayers.get());
                    continue;
                }
                // Break if find one or more winner.
                break;
            }
            Game gameWinner = generateWinnerResult(game, gamers, lives, history.toString());
            gameRepository.save(gameWinner);
            return gameWinner;
        }).orElseThrow(() -> new AppException("Nenhum jogo foi iniciado."));
    }

    private void saveHistory(StringBuilder history, AtomicReference<List<Gamer>> lostPlayers, WinnerPlayedType winnerPlayedType) {
        history.append("|").append(Optional.of(winnerPlayedType).map(WinnerPlayedType::toString).orElse("")).append(" Derrotou - ");
        history.append(lostPlayers.get()
                .stream()
                .collect(Collectors.groupingBy(Gamer::getPlayedType))
                .keySet()
                .stream()
                .map(PlayedType::toString)
                .collect(Collectors.joining(", ")));
    }

    private Game generateWinnerResult(Game game, List<Gamer> gamers, List<Gamer> lives, String history) {
        return Game.builder()
                        .uuid(game.getUuid())
                        .gamers(gamers)
                        .history(history)
                        .winner(lives.stream().map(gamer -> {
                            Optional<Player> player = playerService.findPlayer(gamer.getUuid());
                            return player.map(Objects::toString).get() + " - "+ gamer.getPlayedType().toString();
                        }).collect(Collectors.joining(", ")))
                        .build();
    }

}
