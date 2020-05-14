package com.brq.Jokenpo.infrastructure.util;

import com.brq.Jokenpo.domain.dto.GamerDto;
import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.domain.model.Player;

import java.util.UUID;

public class BDTest {

    protected UUID uuid = UUID.randomUUID();
    protected UUID uuid2 = UUID.randomUUID();
    protected UUID uuid3 = UUID.randomUUID();
    protected UUID uuid4 = UUID.randomUUID();
    protected UUID uuid5 = UUID.randomUUID();

    public Player getPlayer() {
        return Player.builder().name("Maria").uuid(uuid).build();
    }

    public Player getPlayer2() { return Player.builder().name("Eduardo").uuid(uuid2).build(); }

    public Player getPlayer3() { return Player.builder().name("Joao").uuid(uuid3).build(); }

    public Player getPlayer4() { return Player.builder().name("Juliana").uuid(uuid4).build(); }

    public Player getPlayer5() { return Player.builder().name("Ana").uuid(uuid5).build(); }

    public GamerDto getGameDto() { return GamerDto.builder().uuid(uuid).playedType(PlayedType.ROCK).build(); }

    public GamerDto getGameDto2() { return GamerDto.builder().uuid(uuid2).playedType(PlayedType.PAPER).build(); }

    public GamerDto getGameDto3() { return GamerDto.builder().uuid(uuid3).playedType(PlayedType.SCISSORS).build(); }

    public GamerDto getGameDto4() { return GamerDto.builder().uuid(uuid4).playedType(PlayedType.LIZARD).build(); }

    public GamerDto getGameDto5() { return GamerDto.builder().uuid(uuid5).playedType(PlayedType.SPOCK).build(); }

}
