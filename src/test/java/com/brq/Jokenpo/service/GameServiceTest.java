package com.brq.Jokenpo.service;

import com.brq.Jokenpo.AbstractTest;
import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.service.GameService;
import com.brq.Jokenpo.domain.service.PlayerService;
import com.brq.Jokenpo.infrastructure.exception.AppException;
import com.brq.Jokenpo.infrastructure.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceTest extends AbstractTest {

    @Autowired
    protected GameService gameService;

    @MockBean
    protected GameRepository gameRepository;

    @MockBean
    protected PlayerService playerService;

    @Test
    public void when_create_game() {
        Mockito.when(playerService.findPlayer(ArgumentMatchers.any())).thenReturn(Optional.of(super.getPlayer()));
        Mockito.when(gameRepository.addGamer(ArgumentMatchers.any())).thenReturn(super.getGame());
        Game game = gameService.addGamer(getGameDto());

        assertThat(game.getUuid()).isEqualTo(super.getPlayer().getUuid());
    }

    @Test
    public void when_create_game_throwException() {
        Mockito.when(playerService.findPlayer(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(AppException.class, () -> gameService.addGamer(super.getGameDto()));
        assertEquals("Jogador não cadastrado", exception.getMessage());
    }

    @Test
    public void when_delete_game() {
        Mockito.when(gameRepository.find(ArgumentMatchers.any())).thenReturn(Optional.of(super.getGame()));
        Mockito.when(gameRepository.delete(ArgumentMatchers.any())).thenReturn(true);
        boolean isDelete = gameService.deleteGamer(ArgumentMatchers.any());

        assertThat(isDelete).isTrue();
    }

    @Test
    public void when_delete_game_throwException() {
        Mockito.when(gameRepository.find(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(AppException.class, () -> gameService.deleteGamer(ArgumentMatchers.any()));
        assertEquals("Jogador não cadastrado", exception.getMessage());
    }

    @Test
    public void when_all_game() {
            Mockito.when(gameRepository.findAll()).thenReturn(Collections.singleton(super.getGame()));
        Collection<Game> players = gameService.allGames();

        assertThat(players).hasSize(1);
    }

    @Test
    public void when_finish_game() {
        Mockito.when(playerService.findPlayer(ArgumentMatchers.any())).thenReturn(Optional.of(super.getPlayer2()));
        Mockito.when(gameRepository.findGamerOpen()).thenReturn(Optional.of(getGame()));
        Game game = gameService.finishGame();

        assertThat(game.getWinner()).isEqualTo(String.format("%s - %s", super.getPlayer2().getName(), PlayedType.PAPER.toString()));
    }

    @Test
    public void when_already_finish_game() {
        Mockito.when(gameRepository.findGamerOpen()).thenReturn(Optional.empty());
        Throwable exception = assertThrows(AppException.class, () -> gameService.finishGame());
        assertEquals("Nenhum jogo foi iniciado.", exception.getMessage());
    }
}
