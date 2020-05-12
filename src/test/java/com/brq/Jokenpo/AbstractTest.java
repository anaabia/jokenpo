package com.brq.Jokenpo;

import com.brq.Jokenpo.domain.dto.PlayerDto;
import com.brq.Jokenpo.domain.model.Player;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    protected UUID uuid = UUID.randomUUID();

    public Player getPlayerToSave() {
        return Player.builder().name("Maria").build();
    }

    public Player getPlayer() {
        return Player.builder().name("Maria").uuid(uuid).build();
    }

    public Player getPlayer2() {
        return Player.builder().name("Eduardo").uuid(UUID.randomUUID()).build();
    }

    public Player getPlayerUpdate() {
        return Player.builder().name("Maria L").uuid(uuid).build();
    }

    public PlayerDto getPlayerDto() {
        return PlayerDto.builder().name("Maria").build();
    }

}
