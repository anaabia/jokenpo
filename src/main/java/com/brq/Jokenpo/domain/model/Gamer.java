package com.brq.Jokenpo.domain.model;

import com.brq.Jokenpo.domain.enums.PlayedType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gamer {

    private UUID uuid;

    private PlayedType playedType;

    @Override
    public String toString() {
        return uuid.toString() + " - " +playedType ;
    }
}
