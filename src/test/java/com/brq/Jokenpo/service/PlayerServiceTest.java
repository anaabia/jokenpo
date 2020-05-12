package com.brq.Jokenpo.service;

import com.brq.Jokenpo.AbstractTest;
import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.domain.service.PlayerService;
import com.brq.Jokenpo.infrastructure.exception.AppException;
import com.brq.Jokenpo.infrastructure.repository.PlayerRepository;
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

public class PlayerServiceTest extends AbstractTest {

    @Autowired
    protected PlayerService playerService;

    @MockBean
    protected PlayerRepository playerRepository;

    @Test
    public void when_create_player() {
        Mockito.when(playerRepository.findName(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Mockito.when(playerRepository.save(ArgumentMatchers.any())).thenReturn(super.getPlayer());
        Player player = playerService.createPlayer(getPlayerDto());

        assertThat(player.getUuid()).isEqualTo(super.getPlayer().getUuid());
    }

    @Test
    public void when_create_player_throwException() {
        Mockito.when(playerRepository.findName(ArgumentMatchers.anyString())).thenReturn(Optional.of(super.getPlayer()));
        Throwable exception = assertThrows(AppException.class, () -> playerService.createPlayer(super.getPlayerDto()));
        assertEquals("Jogador já cadastrado", exception.getMessage());
    }

    @Test
    public void when_update_player() {
        Mockito.when(playerRepository.find(ArgumentMatchers.any())).thenReturn(Optional.of(super.getPlayer()));
        Mockito.when(playerRepository.save(ArgumentMatchers.any())).thenReturn(super.getPlayerUpdate());
        Player player = playerService.updatePlayer(super.getPlayer().getUuid(), super.getPlayerUpdate().getName());

        assertThat(player.getName()).isEqualTo(getPlayerUpdate().getName());
        assertThat(player.getName()).isNotEqualTo(getPlayer().getName());
    }

    @Test
    public void when_update_player_throwException() {
        Mockito.when(playerRepository.find(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(AppException.class, () -> playerService.updatePlayer(super.getPlayer().getUuid(), super.getPlayerUpdate().getName()));
        assertEquals("Jogador não cadastrado", exception.getMessage());
    }

    @Test
    public void when_delete_player() {
        Mockito.when(playerRepository.find(ArgumentMatchers.any())).thenReturn(Optional.of(super.getPlayer()));
        Mockito.when(playerRepository.delete(ArgumentMatchers.any())).thenReturn(true);
        boolean isDelete = playerService.deletePlayer(ArgumentMatchers.any());

        assertThat(isDelete).isTrue();
    }

    @Test
    public void when_delete_player_throwException() {
        Mockito.when(playerRepository.find(ArgumentMatchers.any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(AppException.class, () -> playerService.deletePlayer(ArgumentMatchers.any()));
        assertEquals("Jogador não cadastrado", exception.getMessage());
    }

    @Test
    public void when_all_player() {
            Mockito.when(playerRepository.findAll()).thenReturn(Collections.singleton(super.getPlayer()));
        Collection<Player> players = playerService.allPlayers();

        assertThat(players).hasSize(1);
    }
}
