package com.brq.Jokenpo.controller;

import com.brq.Jokenpo.AbstractTest;
import com.brq.Jokenpo.domain.model.Player;
import com.brq.Jokenpo.infrastructure.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerControllerTest extends AbstractTest {

    private static final String PLAYER_JSON = "{ \"name\": \"%s\" }";

    @Autowired
    protected PlayerRepository playerRepository;

    private Player player;
    private Player player2;

    @BeforeAll
    public void setup()  {
        player = playerRepository.save(getPlayerToSave());
        player2 = playerRepository.save(getPlayer2());
    }

    @Test
    public void shouldSavePlayer() throws Exception {
        String json = String.format(PLAYER_JSON, "Ana");
        mockMvc.perform(post("/api/player")
                .contentType(MediaType.parseMediaType("application/json"))
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("uuid").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void shouldUpdatePlayer() throws Exception {
        String json = String.format(PLAYER_JSON, "Ana B");
        mockMvc.perform(put("/api/player/" + player.getUuid())
                .contentType(MediaType.parseMediaType("application/json"))
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Ana B"))
                .andDo(print());
    }

    @Test
    public void shouldDeletePlayer() throws Exception {
        mockMvc.perform(delete("/api/player/" + player2.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldListPlayer() throws Exception {
        mockMvc.perform(get("/api/player"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].uuid").isNotEmpty())
                .andDo(print());
    }
}
