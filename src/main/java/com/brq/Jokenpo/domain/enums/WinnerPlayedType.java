package com.brq.Jokenpo.domain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum WinnerPlayedType {

    ROCK(PlayedType.ROCK, Arrays.asList(PlayedType.SCISSORS, PlayedType.LIZARD)),
    PAPER(PlayedType.PAPER, Arrays.asList(PlayedType.ROCK, PlayedType.SPOCK)),
    SCISSORS(PlayedType.SCISSORS, Arrays.asList(PlayedType.LIZARD, PlayedType.PAPER)),
    SPOCK(PlayedType.SPOCK, Arrays.asList(PlayedType.ROCK, PlayedType.SCISSORS)),
    LIZARD(PlayedType.LIZARD, Arrays.asList(PlayedType.PAPER, PlayedType.SPOCK));

    private PlayedType idType;
    private List<PlayedType> lostType;

    WinnerPlayedType(PlayedType idType, List<PlayedType> lostType){
        this.idType = idType;
        this.lostType = lostType;
    }

    public List<PlayedType> getLostType() {
        return lostType;
    }

    public static WinnerPlayedType getWeakType(PlayedType playedType){
        return Stream.of(values()).filter(lost -> lost.idType.equals(playedType)).findFirst().orElse(null);
    }
}