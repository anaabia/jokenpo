package com.brq.Jokenpo.domain.dto;

import com.brq.Jokenpo.domain.model.Player;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {

    @NotNull(message = "Nome do jogador não pode ser nulo")
    @NotBlank(message = "Nome do jogador não pode ser vazio")
    private String name;

    public Player convertToPlayer(){
        return Player.builder().name(name).build();
    }
}
