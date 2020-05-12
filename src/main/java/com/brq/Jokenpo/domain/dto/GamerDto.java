package com.brq.Jokenpo.domain.dto;

import com.brq.Jokenpo.domain.enums.PlayedType;
import com.brq.Jokenpo.domain.model.Gamer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamerDto {

    @NotNull(message = "Codigo do jogador não pode ser nulo")
    @NotBlank(message = "Codigo do jogador não pode ser vazio")
    private UUID uuid;

    @NotNull(message = "Jogada não pode ser nulo")
    @NotBlank(message = "Jogada não pode ser vazio")
    private PlayedType playedType;

    public Gamer convertToGamer(){
        return Gamer.builder().uuid(uuid).playedType(playedType).build();
    }
}
