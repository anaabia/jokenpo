package com.brq.Jokenpo.controller;

import com.brq.Jokenpo.AbstractTest;
import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.infrastructure.repository.GameRepository;
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
public class GameControllerTest extends AbstractTest {

    private static final String GAMER_JSON = "{ \"uuid\": \"%s\", \"playedType\": \"%s\"}";

    @Autowired
    protected GameRepository gameRepository;

    @Autowired
    protected PlayerRepository playerRepository;

    @BeforeAll
    public void setup()  {
        gameRepository.addGamer(getGameDto().convertToGamer());
        gameRepository.addGamer(getGameDto3().convertToGamer());
        playerRepository.save(getPlayer());
        playerRepository.save(getPlayer2());
        playerRepository.save(getPlayer3());
    }

    @Test
    public void shouldSaveGame() throws Exception {
        String json = String.format(GAMER_JSON, getGameDto2().getUuid(), getGameDto2().getPlayedType());
        mockMvc.perform(post("/api/game")
                .contentType(MediaType.parseMediaType("application/json"))
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gamers[?(@.uuid=='"+getGameDto2().getUuid()+"')]").exists())
                .andExpect(jsonPath("$.gamers[?(@.playedType=='"+getGameDto2().getPlayedType()+"')]").exists())
                .andDo(print());
    }

    @Test
    public void shouldUpdateGame() throws Exception {
        String json = String.format(GAMER_JSON, getGameDto().getUuid(), PlayedType.SPOCK);
        mockMvc.perform(post("/api/game")
                .contentType(MediaType.parseMediaType("application/json"))
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gamers[?(@.uuid=='"+getGameDto().getUuid()+"')]").exists())
                .andExpect(jsonPath("$.gamers[?(@.playedType=='"+PlayedType.SPOCK+"')]").exists())
                .andDo(print());
    }

    @Test
    public void shouldDeleteGame() throws Exception {
        mockMvc.perform(delete("/api/game/" + getGameDto3().getUuid()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldListGame() throws Exception {
        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].uuid").isNotEmpty())
                .andDo(print());
    }
}
