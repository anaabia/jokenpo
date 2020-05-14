package com.brq.Jokenpo;

import com.brq.Jokenpo.domain.dto.GamerDto;
import com.brq.Jokenpo.domain.dto.PlayerDto;
import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.domain.model.Game;
import com.brq.Jokenpo.domain.model.Player;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    protected UUID uuid = UUID.randomUUID();
    protected UUID uuid2 = UUID.randomUUID();
    protected UUID uuid3 = UUID.randomUUID();

    public Player getPlayerToSave() {
        return Player.builder().name("Maria").build();
    }

    public Player getPlayer() {
        return Player.builder().name("Maria").uuid(uuid).build();
    }

    public Player getPlayer2() { return Player.builder().name("Eduardo").uuid(uuid2).build(); }

    public Player getPlayer3() { return Player.builder().name("Joao").uuid(uuid3).build(); }

    public Player getPlayerUpdate() { return Player.builder().name("Maria L").uuid(uuid).build(); }

    public PlayerDto getPlayerDto() {
        return PlayerDto.builder().name("Maria").build();
    }

    public GamerDto getGameDto() { return GamerDto.builder().uuid(uuid).playedType(PlayedType.ROCK).build(); }

    public GamerDto getGameDto2() { return GamerDto.builder().uuid(uuid2).playedType(PlayedType.PAPER).build(); }

    public GamerDto getGameDto3() { return GamerDto.builder().uuid(uuid3).playedType(PlayedType.ROCK).build(); }

    public Game getGame() { return Game.builder().gamers(Arrays.asList(getGameDto().convertToGamer(), getGameDto2().convertToGamer(), getGameDto3().convertToGamer())).uuid(uuid).winner(null).build(); }
}
