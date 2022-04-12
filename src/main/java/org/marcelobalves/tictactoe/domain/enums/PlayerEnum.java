package org.marcelobalves.tictactoe.domain.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Player Enum
 *
 * Available players list
 */
public enum PlayerEnum {
    NOUGTH(0),
    CROSS(1);

    private final Integer value;

    PlayerEnum(int value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static PlayerEnum get(int value) {
        Optional<PlayerEnum> playerEnum = Arrays.stream(PlayerEnum.values()).filter(e -> e.getValue()==value).findFirst();
        return playerEnum.orElse(null);
    }
    public static int next(int value) {
        return (value + 1) % PlayerEnum.values().length;
    }
}
