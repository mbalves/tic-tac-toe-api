package org.marcelobalves.tictactoe.domain.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Status Enum
 *
 * Available Game status
 */
public enum StatusEnum {
    NEW(0),
    RUNNING(1),
    FINISHED(2);

    private final int value;

    StatusEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusEnum get(int value){
        Optional<StatusEnum> statusEnum = Arrays.stream(StatusEnum.values()).filter(e -> e.getValue()==value).findFirst();
        return statusEnum.orElse(null);
    }

}
